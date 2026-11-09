package dev.hybridlabs.aquatic.client.render.particle

import dev.hybridlabs.aquatic.particle.*
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry

object HAParticleRendererRegistry {
    private val particleFactoryRegistry = ParticleProviderRegistry.getInstance()

    init {
        particleFactoryRegistry.register(HAParticleTypes.SARGASSUM.get()) { sprites ->
            SargassumParticle.Companion.Provider(sprites)
        }
        particleFactoryRegistry.register(HAParticleTypes.BRINE_BUBBLE.get()) { sprites ->
            BrineBubbleParticle.Companion.Provider(sprites)
        }
        particleFactoryRegistry.register(HAParticleTypes.BRINE_BUBBLE_POP.get()) { sprites ->
            BrineBubblePopParticle.Companion.Provider(sprites)
        }
        particleFactoryRegistry.register(HAParticleTypes.VENT_SMOKE.get()) { sprites ->
            VentSmokeParticle.Companion.Provider(sprites)
        }
    }
}