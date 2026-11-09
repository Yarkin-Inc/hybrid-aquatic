package dev.hybridlabs.aquatic.world.gen.densityfunction

import com.google.gson.JsonElement
import com.mojang.serialization.JsonOps
import com.mojang.serialization.MapCodec
import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.Constants
import dev.hybridlabs.aquatic.mixin.HolderReferenceInvoker
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.RegistryOps
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.levelgen.densityfunction.DfRewriteRule
import net.minecraft.world.level.levelgen.densityfunction.DensitySampler
import net.minecraft.world.level.levelgen.densityfunction.op.FindTopSurfaceFunction
import net.minecraft.util.Interval
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.NoiseRouter

/**
 * Carves trenches into the overworld's noise router before the server loads its levels.
 *
 * When the router reads `minecraft:overworld/offset`, the trench depth wrapper already shapes the trench, so only
 * `hybrid_aquatic:trench/cleanup_carver` runs, clearing terrain that mods like Terralith add inside it. Otherwise, as
 * with Tectonic, `hybrid_aquatic:trench/carver` carves the whole trench and `hybrid_aquatic:trench/biome_depth` makes
 * its floor count as surface, so it gets the trench biomes instead of cave biomes.
 *
 * Carvers apply as a minimum over the router's final and initial density, so they only ever remove terrain. Inside them,
 * `{"type": "hybrid_aquatic:continents_marker"}` stands for the router's continentalness, the same value Biolith places
 * the trench biomes by, so the carved trench always lines up with the trench biomes.
 */
object TrenchCarver {
    private val CARVER = ResourceKey.create(Registries.DENSITY_FUNCTION, CommonClass.locate("trench/carver"))
    private val BIOME_DEPTH = ResourceKey.create(Registries.DENSITY_FUNCTION, CommonClass.locate("trench/biome_depth"))
    private val CLEANUP_CARVER = ResourceKey.create(Registries.DENSITY_FUNCTION, CommonClass.locate("trench/cleanup_carver"))
    private val VANILLA_OFFSET = Identifier.withDefaultNamespace("overworld/offset")

    private val DENSITY_FUNCTION_TYPES = CommonClass.DENSITY_FUNCTION_TYPES
    val CONTINENTS_MARKER = DENSITY_FUNCTION_TYPES.register("continents_marker") { ContinentsMarker.CODEC }

    @JvmStatic
    fun apply(server: MinecraftServer) {
        val overworld = server.registryAccess().lookupOrThrow(Registries.LEVEL_STEM).get(LevelStem.OVERWORLD).orElse(null)?.value() ?: return
        val generator = overworld.generator() as? NoiseBasedChunkGenerator ?: return

        // Inline settings have no registry holder to rebind, which only happens with hand-written world presets
        val settings = generator.generatorSettings() as? Holder.Reference<NoiseGeneratorSettings> ?: run {
            Constants.LOGGER.warn("Overworld noise settings are not registered, so trenches will not be carved")
            return
        }

        val router = settings.value().noiseRouter()
        val densityFunctions = server.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION)
        fun load(key: ResourceKey<DensityFunction>) = DensityFunctionWrappers.rewrite(densityFunctions.getOrThrow(key).value()) { function ->
            if (function is ContinentsMarker) router.continents() else function
        }

        val offsetShapesTrench = reads(router.finalDensity(), VANILLA_OFFSET, server)
        Constants.LOGGER.info(if (offsetShapesTrench) "Trenches follow the overworld offset" else "Overworld terrain skips the offset, so trenches are carved directly")
        val carver = load(if (offsetShapesTrench) CLEANUP_CARVER else CARVER)
        val depth = if (offsetShapesTrench) router.depth() else DensityFunctions.min(router.depth(), load(BIOME_DEPTH))

        // 26.3 stores surface height explicitly. Carve the density used by each surface
        // search, retaining its original search bounds and vertical resolution.
        val carvedSurface = DensityFunctionWrappers.rewrite(router.chunkSurfaceLevel()) { function ->
            if (function is FindTopSurfaceFunction) FindTopSurfaceFunction(
                DensityFunctions.min(function.density(), carver), function.upperBound(),
                function.lowerBound(), function.cellHeight()) else function
        }
        val carvedRouter = NoiseRouter(
            router.temperature(), router.vegetation(), router.continents(), router.erosion(),
            depth, router.ridges(), carvedSurface, DensityFunctions.min(router.finalDensity(), carver),
        )

        val current = settings.value()
        @Suppress("DEPRECATION")
        val carved = NoiseGeneratorSettings(
            current.noiseSettings(),
            current.defaultBlock(),
            current.defaultFluid(),
            carvedRouter,
            current.materialRule(),
            current.spawnTarget(),
            current.seaLevel(),
            current.disableMobGeneration(),
            current.aquifers(),
            current.useLegacyRandomSource(),
            current.debugFunctions(),
        )

        @Suppress("UNCHECKED_CAST")
        (settings as HolderReferenceInvoker<NoiseGeneratorSettings>).`hybridAquatic$bindValue`(carved)
    }

    /**
     * Whether [function] references the registered density function [target], following references through the registry.
     */
    private fun reads(function: DensityFunction, target: Identifier, server: MinecraftServer): Boolean {
        val ops = RegistryOps.create(JsonOps.INSTANCE, server.registryAccess())
        val densityFunctions = server.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION)
        val visited = mutableSetOf<Identifier>()
        val pending = ArrayDeque(listOf(function))

        while (pending.isNotEmpty()) {
            // References to registered functions encode as their ID, so every string in the encoding is a candidate
            val json = DensityFunction.CODEC.encodeStart(ops, pending.removeFirst()).result().orElse(null) ?: continue
            for (id in strings(json).mapNotNull(Identifier::tryParse)) {
                if (id == target) return true
                if (visited.add(id)) densityFunctions.get(ResourceKey.create(Registries.DENSITY_FUNCTION, id)).ifPresent { pending.add(it.value()) }
            }
        }

        return false
    }

    private fun strings(json: JsonElement): Sequence<String> = when {
        json.isJsonPrimitive -> if (json.asJsonPrimitive.isString) sequenceOf(json.asString) else emptySequence()
        json.isJsonArray -> json.asJsonArray.asSequence().flatMap(::strings)
        json.isJsonObject -> json.asJsonObject.entrySet().asSequence().flatMap { strings(it.value) }
        else -> emptySequence()
    }

    /**
     * Placeholder for the router's continentalness. [apply] replaces every marker, so it never reaches world generation.
     */
    object ContinentsMarker : DensityFunction {
        val CODEC: MapCodec<ContinentsMarker> = MapCodec.unit(ContinentsMarker)

        override fun compileSampler(context: DensityFunction.CompileContext): DensitySampler =
            error("Unresolved continents_marker reached world generation")
        override fun rewriteChildren(rule: DfRewriteRule): DensityFunction = this
        override fun range(): Interval = Interval.INFINITE
        override fun domainAxes(): Int = DensityFunction.ALL_AXES
        override fun codec(): MapCodec<out DensityFunction> = CODEC
    }
}
