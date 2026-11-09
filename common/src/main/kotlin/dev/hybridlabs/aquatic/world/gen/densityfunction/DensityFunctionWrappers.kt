package dev.hybridlabs.aquatic.world.gen.densityfunction

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.Constants
import dev.hybridlabs.aquatic.mixin.HolderReferenceInvoker
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.FileToIdConverter
import net.minecraft.resources.RegistryOps
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.levelgen.densityfunction.DfRewriteRule
import net.minecraft.world.level.levelgen.densityfunction.DensitySampler
import net.minecraft.util.Interval
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions

/**
 * Wraps registered density functions with datapack-defined wrappers before the server loads its levels.
 *
 * Each file in `data/<namespace>/hybrid_aquatic/density_function_wrapper/` names a `target_function` and a
 * `wrapper_function`. Inside the wrapper, `{"type": "hybrid_aquatic:wrapped_marker"}` stands for the target's
 * current value. Files apply in resource location order, so a later wrapper wraps the result of an earlier one.
 */
object DensityFunctionWrappers {
    private val WRAPPER_FILES = FileToIdConverter.json("${Constants.MOD_ID}/density_function_wrapper")

    private val DENSITY_FUNCTION_TYPES = CommonClass.DENSITY_FUNCTION_TYPES
    val WRAPPED_MARKER = DENSITY_FUNCTION_TYPES.register("wrapped_marker") { WrappedMarker.CODEC }

    private data class Wrapper(val targetFunction: Identifier, val wrapperFunction: DensityFunction) {
        companion object {
            val CODEC: Codec<Wrapper> = RecordCodecBuilder.create { instance ->
                instance.group(
                    Identifier.CODEC.fieldOf("target_function").forGetter(Wrapper::targetFunction),
                    DensityFunction.CODEC.fieldOf("wrapper_function").forGetter(Wrapper::wrapperFunction),
                ).apply(instance, ::Wrapper)
            }
        }
    }

    @JvmStatic
    fun apply(server: MinecraftServer) {
        val ops = RegistryOps.create(JsonOps.INSTANCE, server.registryAccess())
        val densityFunctions = server.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION)

        WRAPPER_FILES.listMatchingResources(server.resourceManager).toSortedMap().forEach { (file, resource) ->
            val id = WRAPPER_FILES.fileToId(file)
            val json = resource.openAsReader().use(JsonParser::parseReader)
            val wrapper = Wrapper.CODEC.parse(ops, json)
                .getOrThrow { error -> IllegalStateException("Invalid density function wrapper $id: $error") }

            val target = densityFunctions.get(ResourceKey.create(Registries.DENSITY_FUNCTION, wrapper.targetFunction))
                .orElseThrow { IllegalStateException("Density function wrapper $id targets unknown density function ${wrapper.targetFunction}") }

            val wrapped = target.value()
            val full = rewrite(wrapper.wrapperFunction) { function -> if (function is WrappedMarker) wrapped else function }

            @Suppress("UNCHECKED_CAST")
            (target as HolderReferenceInvoker<DensityFunction>).`hybridAquatic$bindValue`(full)
        }
    }

    /** Rewrite children recursively, preserving registry references unless their contents change. */
    fun rewrite(root: DensityFunction, transform: (DensityFunction) -> DensityFunction): DensityFunction {
        val rule = object : DfRewriteRule {
            override fun rewrite(function: DensityFunction): DensityFunction {
                val replacement = transform(function)
                return if (replacement !== function) replacement else function.rewriteChildren(this)
            }
        }
        return rule.rewrite(root)
    }

    /**
     * Placeholder for the wrapped function. [apply] replaces every marker, so it never reaches world generation.
     */
    object WrappedMarker : DensityFunction {
        val CODEC: MapCodec<WrappedMarker> = MapCodec.unit(WrappedMarker)

        override fun compileSampler(context: DensityFunction.CompileContext): DensitySampler =
            error("Unresolved wrapped_marker reached world generation")
        override fun rewriteChildren(rule: DfRewriteRule): DensityFunction = this
        override fun range(): Interval = Interval.INFINITE
        override fun domainAxes(): Int = DensityFunction.ALL_AXES
        override fun codec(): MapCodec<out DensityFunction> = CODEC
    }
}
