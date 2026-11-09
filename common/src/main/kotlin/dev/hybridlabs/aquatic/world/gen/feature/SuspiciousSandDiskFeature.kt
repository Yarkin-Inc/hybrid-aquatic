package dev.hybridlabs.aquatic.world.gen.feature

import com.mojang.serialization.Codec
import dev.hybridlabs.aquatic.loot.HALootTables
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntityType
import dev.hybridlabs.aquatic.world.gen.feature.FeatureAlgorithm as Feature
import dev.hybridlabs.aquatic.world.gen.feature.FeatureContext as FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.DiskFeature as DiskConfiguration

class SuspiciousSandDiskFeature(
    codec: Codec<DiskConfiguration>
) : Feature<DiskConfiguration>(codec) {

    override fun place(context: FeaturePlaceContext<DiskConfiguration>): Boolean {
        val placed = context.config().place(context.level(), context.chunkGenerator(), context.random(), context.origin())
        if (!placed) return false

        val level = context.level()
        val origin = context.origin()

        val radius = context.config().radius().sample(context.random())
        val halfHeight = context.config().halfHeight()

        BlockPos.betweenClosed(
            origin.offset(-radius, -halfHeight, -radius),
            origin.offset(radius, halfHeight, radius)
        ).forEach { pos ->
            if (level.getBlockState(pos).`is`(Blocks.SUSPICIOUS_SAND)) {
                level.getBlockEntity(pos, net.minecraft.world.level.block.entity.BlockEntityTypes.BRUSHABLE_BLOCK)
                    .ifPresent { brushable ->
                        brushable.setLootTable(
                            (ResourceKey.create(
                                Registries.LOOT_TABLE,
                                HALootTables.BEACH_ARCHAEOLOGY_ID)),
                            pos.asLong()
                        )
                    }
            }
        }

        return true
    }
}