package dev.hybridlabs.aquatic.particle

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.platform.registration.RegistryObject
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import java.util.function.Supplier

object HAParticleTypes {
    // val WATER_UP_WHIRL = register("water_up_whirl") { net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true) }
    val SARGASSUM = register("sargassum") { net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true) }
    val VENT_SMOKE = register("vent_smoke") { net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true) }
    val BRINE_BUBBLE = register("brine_bubble") { net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true) }
    val BRINE_BUBBLE_POP = register("brine_bubble_pop") { net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true) }

    fun <T: ParticleType<SimpleParticleType>> register(id: String, particle: Supplier<T>): RegistryObject<T> {
        return CommonClass.PARTICLE_TYPE.register(id,  particle)
    }
}