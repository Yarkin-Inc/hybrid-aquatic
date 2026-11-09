package dev.hybridlabs.aquatic.world.gen.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.world.gen.feature.config.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

data class BrineLakeFeatureConfig(
    val barrierProvider: BlockStateProvider,
    val fluidProvider: BlockStateProvider,
) : FeatureConfiguration {
    companion object {
        val CODEC: Codec<BrineLakeFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockStateProvider.DIRECT_CODEC.fieldOf("barrier_block").forGetter(BrineLakeFeatureConfig::barrierProvider),
                BlockStateProvider.DIRECT_CODEC.fieldOf("fluid_block").forGetter(BrineLakeFeatureConfig::fluidProvider),
            ).apply(instance, ::BrineLakeFeatureConfig)
        }
    }
}