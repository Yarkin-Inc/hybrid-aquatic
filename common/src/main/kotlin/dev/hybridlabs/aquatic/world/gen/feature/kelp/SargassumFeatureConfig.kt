package dev.hybridlabs.aquatic.world.gen.feature.kelp

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.world.gen.feature.config.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

data class SargassumFeatureConfig(val toPlace: BlockStateProvider) : FeatureConfiguration {
    companion object {
        val CODEC: Codec<SargassumFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockStateProvider.DIRECT_CODEC.fieldOf("to_place")
                    .forGetter(SargassumFeatureConfig::toPlace)
            ).apply(instance, ::SargassumFeatureConfig)
        }
    }
}