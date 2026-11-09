package dev.hybridlabs.aquatic.block

import dev.hybridlabs.aquatic.block.wood.HAWoodTypes
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.material.MapColor

object HAPlatformBlocks {
    val DRIFTWOOD_LOG =
        HABlocks.register("driftwood_log") { key -> RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy((Blocks.OAK_PLANKS)).setId(key)) }
    val STRIPPED_DRIFTWOOD_LOG =
        HABlocks.register("stripped_driftwood_log") { key -> RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy((Blocks.OAK_PLANKS)).setId(key)) }
    val DRIFTWOOD_WOOD =
        HABlocks.register("driftwood_wood") { key -> RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy((Blocks.OAK_PLANKS)).setId(key)) }
    val STRIPPED_DRIFTWOOD_WOOD =
        HABlocks.register("stripped_driftwood_wood") { key -> RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy((Blocks.OAK_PLANKS)).setId(key)) }
    val DRIFTWOOD_PLANKS =
        HABlocks.register("driftwood_planks") { key -> Block(BlockBehaviour.Properties.ofFullCopy((Blocks.OAK_PLANKS)).setId(key)) }
    val DRIFTWOOD_STAIRS = HABlocks.register(
        "driftwood_stairs"
    ) { key -> StairBlock(DRIFTWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(key)) }
    val DRIFTWOOD_SLAB =
        HABlocks.register("driftwood_slab") { key -> SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(key)) }
    val DRIFTWOOD_BUTTON = HABlocks.register(
        "driftwood_button"
    ) { key ->
        ButtonBlock(
            BlockSetType.OAK, 25,
            BlockBehaviour.Properties.ofFullCopy(
                Blocks.OAK_BUTTON
            ).setId(key)
        )
    }
    val DRIFTWOOD_PRESSURE_PLATE = HABlocks.register(
        "driftwood_pressure_plate"
    ) { key ->
        PressurePlateBlock(
            BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).setId(key)
        )
    }
    val DRIFTWOOD_FENCE =
        HABlocks.register("driftwood_fence") { key -> FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(key)) }
    val DRIFTWOOD_FENCE_GATE = HABlocks.register(
        "driftwood_fence_gate"
    ) { key ->
        FenceGateBlock(
            HAWoodTypes.DRIFTWOOD,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(key)
        )
    }
    val DRIFTWOOD_DOOR =
        HABlocks.register("driftwood_door") { key ->
            DoorBlock(
                BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).setId(key)
            )
        }
    val DRIFTWOOD_TRAPDOOR =
        HABlocks.register("driftwood_trapdoor") { key ->
            TrapDoorBlock(
                BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).setId(key)
            )
        }

    val DUNEGRASS = HABlocks.register("dunegrass") { key -> DunegrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).setId(key)) }
    val TALL_DUNEGRASS =
        HABlocks.register("tall_dunegrass") { key -> TallDunegrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).setId(key)) }
    val CATTAIL = HABlocks.register("cattail") { key -> CattailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).setId(key)) }

    val GLOWSLIME_BLOCK = HABlocks.register("glowslime_block") { key ->
        GlowslimeBlock(
            BlockBehaviour.Properties.of().setId(key)
            .friction(0.8F)
            .instabreak()
            .noOcclusion()
            .sound(SoundType.SLIME_BLOCK)
            .mapColor { MapColor.COLOR_CYAN }
            .lightLevel { 14 }
        )
    }

    val HAGSLIME_BLOCK = HABlocks.register("hagslime_block") { key ->
        HagslimeBlock(
            BlockBehaviour.Properties.of().setId(key)
                .instabreak()
                .noOcclusion()
                .sound(SoundType.HONEY_BLOCK)
                .mapColor { MapColor.TERRACOTTA_WHITE }
                .isSuffocating { _, _, _ -> false }
                .speedFactor(0.4F)
                .jumpFactor(0.5F)
        )
    }
}