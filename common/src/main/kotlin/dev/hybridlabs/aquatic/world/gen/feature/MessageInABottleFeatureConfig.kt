package dev.hybridlabs.aquatic.world.gen.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.world.gen.feature.config.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

data class MessageInABottleFeatureConfig(val toPlace: BlockStateProvider) : FeatureConfiguration {
    companion object {
        /**
         * The codec for this class.
         */
        val CODEC: Codec<MessageInABottleFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockStateProvider.DIRECT_CODEC.fieldOf("to_place")
                    .forGetter(MessageInABottleFeatureConfig::toPlace)
            ).apply(instance, ::MessageInABottleFeatureConfig)
        }
    }
}