package dev.hybridlabs.aquatic.world.gen.feature.kelp

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.world.gen.feature.config.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

data class BullKelpFeatureConfig(val toPlace: BlockStateProvider) : FeatureConfiguration {
    companion object {
        val CODEC: Codec<BullKelpFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockStateProvider.DIRECT_CODEC.fieldOf("to_place")
                    .forGetter(BullKelpFeatureConfig::toPlace)
            ).apply(instance, ::BullKelpFeatureConfig)
        }
    }
}