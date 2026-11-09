package dev.hybridlabs.aquatic.block

import com.google.common.collect.ImmutableSet
import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.platform.registration.RegistryObject
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.ColorRGBA
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import java.util.function.Supplier


/**
 * The registry of all blocks in Hybrid Aquatic.
 */
object HABlocks {
    val ANEMONE = register("anemone") { key ->
        AnemoneBlock(Properties.of().setId(key)
            .mapColor(MapColor.TERRACOTTA_PINK)
            .randomTicks()
            .strength(0.4f)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
            .sound(SoundType.SLIME_BLOCK)
            .noTerrainParticles()
        )
    }

    val GIANT_GREEN_ANEMONE = register("giant_green_anemone") { key ->
        GiantGreenAnemoneBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .randomTicks()
            .strength(0.4f)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
            .sound(SoundType.SLIME_BLOCK)
            .noTerrainParticles()
        )
    }

    val STRAWBERRY_ANEMONE = register("strawberry_anemone") { key ->
        StrawberryAnemoneBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_RED)
            .randomTicks()
            .strength(0.4f)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
            .sound(SoundType.SLIME_BLOCK)
            .noTerrainParticles()
        )
    }

    val MESSAGE_IN_A_BOTTLE = register("message_in_a_bottle") { key ->
        MessageInABottleBlock(Properties.ofFullCopy(Blocks.GLASS).setId(key)
            .instabreak()
            .pushReaction(PushReaction.POPPED)
            .noCollision()
            .noTerrainParticles()
        )
    }

    val CLAMS = register("clams") { key -> ClamBlock(Properties.ofFullCopy(Blocks.CARROTS).setId(key)) }
    val MUSSELS = register("mussels") { key -> MusselBlock(Properties.ofFullCopy(Blocks.CARROTS).setId(key)) }
    val WILD_MUSSELS = register("wild_mussels") { key -> WildMusselBlock(Properties.ofFullCopy(Blocks.TALL_GRASS).setId(key).overrideLootTable(MUSSELS.get().lootTable)) }
    val AERATED_SAND = register("aerated_sand") { key -> AeratedSandBlock(ColorRGBA(14406560), Properties.ofFullCopy(Blocks.SAND).setId(key).postProcess { _, _, pos -> pos }) }
    val BUBBLE_GEYSER = register("bubble_geyser") { key -> BubbleGeyserBlock(ColorRGBA(14406560), Properties.ofFullCopy(Blocks.SAND).setId(key).postProcess { _, _, pos -> pos }) }
    val WHITE_SAND = register("white_sand") { key -> ColoredFallingBlock(ColorRGBA(14406560), Properties.ofFullCopy(Blocks.SAND).setId(key)) }

    val WHITE_SANDSTONE = register("white_sandstone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.SANDSTONE).setId(key)) }
    val WHITE_SANDSTONE_STAIRS = register("white_sandstone_stairs") { key ->
        StairBlock(WHITE_SANDSTONE.get().defaultBlockState(), Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val WHITE_SANDSTONE_SLAB = register("white_sandstone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val WHITE_SANDSTONE_WALL = register("white_sandstone_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)) }

    val SMOOTH_WHITE_SANDSTONE = register("smooth_white_sandstone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.SANDSTONE).setId(key)) }
    val SMOOTH_WHITE_SANDSTONE_SLAB = register("smooth_white_sandstone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val SMOOTH_WHITE_SANDSTONE_STAIRS = register("smooth_white_sandstone_stairs") { key ->
        StairBlock(WHITE_SANDSTONE.get().defaultBlockState(), Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }

    val CUT_WHITE_SANDSTONE = register("cut_white_sandstone") { key ->
        Block(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val CUT_WHITE_SANDSTONE_SLAB = register("cut_white_sandstone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }

    val CHISELED_WHITE_SANDSTONE = register("chiseled_white_sandstone") { key ->
        Block(Properties
            .ofFullCopy(WHITE_SANDSTONE.get()).setId(key)
            .mapColor(DyeColor.WHITE)) }

    val BONE_STAIRS = register("bone_stairs") { key ->
        StairBlock(Blocks.BONE_BLOCK.defaultBlockState(), Properties
            .ofFullCopy(Blocks.BONE_BLOCK).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val BONE_SLAB = register("bone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(Blocks.BONE_BLOCK).setId(key)
            .mapColor(DyeColor.WHITE)) }
    val BONE_WALL = register("bone_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(Blocks.BONE_BLOCK).setId(key)) }
    val BONE_FENCE = register("bone_fence") { key ->
        FenceBlock(Properties
            .ofFullCopy(Blocks.BONE_BLOCK).setId(key)) }

    val SUSPICIOUS_RED_SAND = register("suspicious_red_sand") { key ->
        BrushableBlock(Blocks.RED_SAND,
            SoundEvents.BRUSH_SAND,
            SoundEvents.BRUSH_SAND_COMPLETED,
        Properties.ofFullCopy(Blocks.RED_SAND).setId(key)) }

    val CRYSTALLINE_SULFUR = register("crystalline_sulfur") { key -> CrystallineSulfurBlock(Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).setId(key)) }
    val GRASSY_SAND = register("grassy_sand") { key -> GrassySandBlock(Properties.ofFullCopy(Blocks.SAND).setId(key)) }
    val DEPTH_CHARGE = register("depth_charge") { key -> DepthChargeBlock(Properties.ofFullCopy(Blocks.TNT).setId(key)) }
    val CORALSTONE = register("coralstone") { key -> Block(Properties.ofFullCopy(Blocks.SANDSTONE).setId(key)) }
    val SHORESTONE = register("shorestone") { key -> Block(Properties.ofFullCopy(Blocks.SANDSTONE).setId(key)) }
    val BARNACLE_SHORESTONE = register("barnacle_shorestone") { key -> Block(Properties.ofFullCopy(Blocks.SANDSTONE).setId(key)) }

    val MARINE_SNOW = register("marine_snow") { key -> ColoredFallingBlock(ColorRGBA(14406560), Properties.ofFullCopy(Blocks.MUD).setId(key)) }

    val RED_BRINESTONE = register("red_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.DEEPSLATE).setId(key)) }
    val RED_BRINESTONE_STAIRS = register("red_brinestone_stairs") { key ->
        StairBlock(RED_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(RED_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.RED)) }
    val RED_BRINESTONE_SLAB = register("red_brinestone_slab") { key ->
        SlabBlock(Properties
        .ofFullCopy(RED_BRINESTONE.get()).setId(key)
        .mapColor(DyeColor.RED)) }
    val RED_BRINESTONE_WALL = register("red_brinestone_wall") { key ->
        WallBlock(Properties
        .ofFullCopy(RED_BRINESTONE.get()).setId(key)) }

    val RED_BRINESTONE_BRICKS = register("red_brinestone_bricks") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val RED_BRINESTONE_BRICK_STAIRS = register("red_brinestone_brick_stairs") { key ->
        StairBlock(RED_BRINESTONE_BRICKS.get().defaultBlockState(), Properties
            .ofFullCopy(RED_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.RED)) }
    val RED_BRINESTONE_BRICK_SLAB = register("red_brinestone_brick_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(RED_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.RED)) }
    val RED_BRINESTONE_BRICK_WALL = register("red_brinestone_brick_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(RED_BRINESTONE.get()).setId(key)) }

    val CHISELED_RED_BRINESTONE = register("chiseled_red_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_RED_BRINESTONE = register("polished_red_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_RED_BRINESTONE_STAIRS = register("polished_red_brinestone_stairs") { key ->
        StairBlock(POLISHED_RED_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(POLISHED_RED_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.RED)) }
    val POLISHED_RED_BRINESTONE_SLAB = register("polished_red_brinestone_slab") { key ->
        SlabBlock(Properties.ofFullCopy(POLISHED_RED_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.RED)) }

    val ORANGE_BRINESTONE = register("orange_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.DEEPSLATE).setId(key)) }
    val ORANGE_BRINESTONE_STAIRS = register("orange_brinestone_stairs") { key ->
        StairBlock(ORANGE_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }
    val ORANGE_BRINESTONE_SLAB = register("orange_brinestone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }
    val ORANGE_BRINESTONE_WALL = register("orange_brinestone_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)) }

    val ORANGE_BRINESTONE_BRICKS = register("orange_brinestone_bricks") { key ->
        Block(Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val ORANGE_BRINESTONE_BRICK_STAIRS = register("orange_brinestone_brick_stairs") { key ->
        StairBlock(ORANGE_BRINESTONE_BRICKS.get().defaultBlockState(), Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }
    val ORANGE_BRINESTONE_BRICK_SLAB = register("orange_brinestone_brick_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }
    val ORANGE_BRINESTONE_BRICK_WALL = register("orange_brinestone_brick_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(ORANGE_BRINESTONE.get()).setId(key)) }

    val CHISELED_ORANGE_BRINESTONE = register("chiseled_orange_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_ORANGE_BRINESTONE = register("polished_orange_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_ORANGE_BRINESTONE_STAIRS = register("polished_orange_brinestone_stairs") { key ->
        StairBlock(POLISHED_ORANGE_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(POLISHED_ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }
    val POLISHED_ORANGE_BRINESTONE_SLAB = register("polished_orange_brinestone_slab") { key ->
        SlabBlock(Properties.ofFullCopy(POLISHED_ORANGE_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.ORANGE)) }

    val YELLOW_BRINESTONE = register("yellow_brinestone") { key ->
        Block(Properties.ofFullCopy(Blocks.DEEPSLATE).setId(key)) }
    val YELLOW_BRINESTONE_STAIRS = register("yellow_brinestone_stairs") { key ->
        StairBlock(YELLOW_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }
    val YELLOW_BRINESTONE_SLAB = register("yellow_brinestone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }
    val YELLOW_BRINESTONE_WALL = register("yellow_brinestone_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)) }

    val YELLOW_BRINESTONE_BRICKS = register("yellow_brinestone_bricks") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val YELLOW_BRINESTONE_BRICK_STAIRS = register("yellow_brinestone_brick_stairs") { key ->
        StairBlock(YELLOW_BRINESTONE_BRICKS.get().defaultBlockState(), Properties
            .ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }
    val YELLOW_BRINESTONE_BRICK_SLAB = register("yellow_brinestone_brick_slab") { key ->
        SlabBlock(Properties.ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }
    val YELLOW_BRINESTONE_BRICK_WALL = register("yellow_brinestone_brick_wall") { key ->
        WallBlock(Properties.ofFullCopy(YELLOW_BRINESTONE.get()).setId(key)) }

    val CHISELED_YELLOW_BRINESTONE = register("chiseled_yellow_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_YELLOW_BRINESTONE = register("polished_yellow_brinestone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_YELLOW_BRINESTONE_STAIRS = register("polished_yellow_brinestone_stairs") { key ->
        StairBlock(POLISHED_YELLOW_BRINESTONE.get().defaultBlockState(), Properties
            .ofFullCopy(POLISHED_YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }
    val POLISHED_YELLOW_BRINESTONE_SLAB = register("polished_yellow_brinestone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(POLISHED_YELLOW_BRINESTONE.get()).setId(key)
            .mapColor(DyeColor.YELLOW)) }

    val SCHIST = register("schist") { key ->
        Block(Properties
            .ofFullCopy(Blocks.DEEPSLATE).setId(key)) }
    val SCHIST_STAIRS = register("schist_stairs") { key ->
        StairBlock(SCHIST.get().defaultBlockState(), Properties
        .ofFullCopy(SCHIST.get()).setId(key)
        .mapColor(DyeColor.BLACK)) }
    val SCHIST_SLAB = register("schist_slab") { key ->
        SlabBlock(Properties
        .ofFullCopy(SCHIST.get()).setId(key)
        .mapColor(DyeColor.BLACK)) }
    val SCHIST_WALL = register("schist_wall") { key ->
        WallBlock(Properties
        .ofFullCopy(SCHIST.get()).setId(key)) }

    val SCHIST_BRICKS = register("schist_bricks") { key ->
        Block(Properties
            .ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val SCHIST_BRICK_STAIRS = register("schist_brick_stairs") { key ->
        StairBlock(SCHIST_BRICKS.get().defaultBlockState(), Properties
        .ofFullCopy(SCHIST.get()).setId(key).mapColor(DyeColor.BLACK)) }
    val SCHIST_BRICK_SLAB = register("schist_brick_slab") { key ->
        SlabBlock(Properties.ofFullCopy(SCHIST.get()).setId(key)
            .mapColor(DyeColor.BLACK)) }
    val SCHIST_BRICK_WALL = register("schist_brick_wall") { key ->
        WallBlock(Properties.ofFullCopy(SCHIST.get()).setId(key)) }

    val CHISELED_SCHIST = register("chiseled_schist") { key ->
        Block(Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_SCHIST = register("polished_schist") { key ->
        Block(Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE).setId(key)) }
    val POLISHED_SCHIST_STAIRS = register("polished_schist_stairs") { key ->
        StairBlock(POLISHED_SCHIST.get().defaultBlockState(), Properties
            .ofFullCopy(POLISHED_SCHIST.get()).setId(key)
            .mapColor(DyeColor.BLACK)) }
    val POLISHED_SCHIST_SLAB = register("polished_schist_slab") { key ->
        SlabBlock(Properties.ofFullCopy(POLISHED_SCHIST.get()).setId(key)
            .mapColor(DyeColor.BLACK)) }

    val CHIMNEYSTONE = register("chimneystone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.TUFF).setId(key)) }
    val CHIMNEYSTONE_STAIRS = register("chimneystone_stairs") { key ->
        StairBlock(CHIMNEYSTONE.get().defaultBlockState(), Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }
    val CHIMNEYSTONE_SLAB = register("chimneystone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }
    val CHIMNEYSTONE_WALL = register("chimneystone_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)) }

    val CHIMNEYSTONE_BRICKS = register("chimneystone_bricks") { key ->
        Block(Properties
            .ofFullCopy(Blocks.TUFF).setId(key)) }
    val CHIMNEYSTONE_BRICK_STAIRS = register("chimneystone_brick_stairs") { key ->
        StairBlock(CHIMNEYSTONE_BRICKS.get().defaultBlockState(), Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }
    val CHIMNEYSTONE_BRICK_SLAB = register("chimneystone_brick_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }
    val CHIMNEYSTONE_BRICK_WALL = register("chimneystone_brick_wall") { key ->
        WallBlock(Properties
            .ofFullCopy(CHIMNEYSTONE.get()).setId(key)) }

    val CHISELED_CHIMNEYSTONE = register("chiseled_chimneystone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.TUFF).setId(key)) }
    val POLISHED_CHIMNEYSTONE = register("polished_chimneystone") { key ->
        Block(Properties
            .ofFullCopy(Blocks.TUFF).setId(key)) }
    val POLISHED_CHIMNEYSTONE_STAIRS = register("polished_chimneystone_stairs") { key ->
        StairBlock(POLISHED_CHIMNEYSTONE.get().defaultBlockState(), Properties
            .ofFullCopy(POLISHED_CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }
    val POLISHED_CHIMNEYSTONE_SLAB = register("polished_chimneystone_slab") { key ->
        SlabBlock(Properties
            .ofFullCopy(POLISHED_CHIMNEYSTONE.get()).setId(key)
            .mapColor(DyeColor.BROWN)) }

    val BASKING_SHARK_PLUSHIE = register("basking_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.BASKING_SHARK) }
    val BULL_SHARK_PLUSHIE = register("bull_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.BULL_SHARK) }
    val FRILLED_SHARK_PLUSHIE = register("frilled_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.FRILLED_SHARK) }
    val GREAT_WHITE_SHARK_PLUSHIE = register("great_white_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.GREAT_WHITE_SHARK) }
    val HAMMERHEAD_SHARK_PLUSHIE = register("hammerhead_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.HAMMERHEAD_SHARK) }
    val THRESHER_SHARK_PLUSHIE = register("thresher_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.THRESHER_SHARK) }
    val TIGER_SHARK_PLUSHIE = register("tiger_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.TIGER_SHARK) }
    val WHALE_SHARK_PLUSHIE = register("whale_shark_plushie") { key ->
        createPlushieBlock(key, PlushieBlock.Variant.WHALE_SHARK) }

    val PEARL_BLOCK = register("pearl_block") { key -> Block(Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).setId(key)) }
    val BLACK_PEARL_BLOCK = register("black_pearl_block") { key -> Block(Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).setId(key)) }

    val CRAB_POT = register("crab_pot") { key -> CrateBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(key).noOcclusion()) }
    val HYBRID_CRATE = register("hybrid_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(key)) }
    val OAK_CRATE = register("oak_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(key)) }
    val SPRUCE_CRATE = register("spruce_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).setId(key)) }
    val BIRCH_CRATE = register("birch_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.BIRCH_PLANKS).setId(key)) }
    val DARK_OAK_CRATE = register("dark_oak_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS).setId(key)) }
    val ACACIA_CRATE = register("acacia_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.ACACIA_PLANKS).setId(key)) }
    val JUNGLE_CRATE = register("jungle_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.JUNGLE_PLANKS).setId(key)) }
    val MANGROVE_CRATE = register("mangrove_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.MANGROVE_PLANKS).setId(key)) }
    val CHERRY_CRATE = register("cherry_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.CHERRY_PLANKS).setId(key)) }
    val BAMBOO_CRATE = register("bamboo_crate") { key -> CrateBlock(Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).setId(key)) }

    val SARGASSUM = register("sargassum") { key ->
        SargassumBlock(Properties.ofFullCopy(Blocks.KELP).setId(key)
            .noOcclusion())
    }

    val SARGASSUM_PLANT = register("sargassum_plant") { key ->
        SargassumPlantBlock(Properties.ofFullCopy(Blocks.KELP_PLANT).setId(key)
            .noOcclusion()
            .overrideLootTable(SARGASSUM.get().lootTable))
    }

    val BULL_KELP = register("bull_kelp") { key ->
        BullKelpBlock(Properties.ofFullCopy(Blocks.KELP).setId(key)
            .noOcclusion())
    }

    val BULL_KELP_PLANT = register("bull_kelp_plant") { key ->
        BullKelpPlantBlock(Properties.ofFullCopy(Blocks.KELP_PLANT).setId(key)
            .noOcclusion()
            .overrideLootTable(BULL_KELP.get().lootTable))
    }

    val DELESSERIA = register("delesseria") { key ->
        DelesseriaBlock(Properties.ofFullCopy(Blocks.KELP).setId(key)
            .noOcclusion())
    }

    val DELESSERIA_PLANT = register("delesseria_plant") { key ->
        DelesseriaPlantBlock(Properties.ofFullCopy(Blocks.KELP_PLANT).setId(key)
            .noOcclusion()
            .overrideLootTable(DELESSERIA.get().lootTable))
    }

    val FLOATING_SARGASSUM = register("floating_sargassum") { key ->
        FloatingSargassumBlock(Properties.ofFullCopy(Blocks.LILY_PAD).setId(key)
            .noCollision()
            .instabreak()
            .mapColor(MapColor.WOOD))
    }

    val WATER_LETTUCE = register("water_lettuce") { key ->
        WaterLettuceBlock(Properties.ofFullCopy(Blocks.LILY_PAD).setId(key)
            .noCollision()
            .instabreak())
    }

    val WATER_HYACINTH = register("water_hyacinth") { key ->
        WaterHyacinthBlock(Properties.ofFullCopy(Blocks.LILY_PAD).setId(key)
            .noCollision()
            .instabreak())
    }

    val JUNGLE_LILY_PAD = register("jungle_lily_pad") { key ->
        JungleLilyPadBlock(Properties.ofFullCopy(Blocks.LILY_PAD).setId(key)
            .instabreak())
    }

    val RAFT = register("raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.OAK_WOOD).setId(key))
    }

    val OAK_RAFT = register("oak_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.OAK_WOOD).setId(key))
    }

    val SPRUCE_RAFT = register("spruce_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.SPRUCE_WOOD).setId(key))
    }

    val DARK_OAK_RAFT = register("dark_oak_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.DARK_OAK_WOOD).setId(key))
    }

    val BIRCH_RAFT = register("birch_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.BIRCH_WOOD).setId(key))
    }

    val CHERRY_RAFT = register("cherry_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.CHERRY_WOOD).setId(key))
    }

    val JUNGLE_RAFT = register("jungle_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.JUNGLE_WOOD).setId(key))
    }

    val ACACIA_RAFT = register("acacia_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.ACACIA_WOOD).setId(key))
    }

    val MANGROVE_RAFT = register("mangrove_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.MANGROVE_WOOD).setId(key))
    }

    val DRIFTWOOD_RAFT = register("driftwood_raft") { key ->
        RaftBlock(Properties.ofFullCopy(Blocks.OAK_WOOD).setId(key))
    }

    val GLOWING_PLANKTON = register("glowing_plankton") { key ->
        GlowingPlanktonBlock(Properties.ofFullCopy(Blocks.LILY_PAD).setId(key)
        .noOcclusion()
        .replaceable()
        .noCollision()
        .instabreak())
    }

    val SHORT_RED_ALGAE = register("short_red_algae") { key -> ShortRedAlgaeBlock(Properties.ofFullCopy(Blocks.SEAGRASS).setId(key).offsetType(BlockBehaviour.OffsetType.XZ)) }
    val RED_ALGAE = register("red_algae") { key -> RedAlgaeBlock(Properties.ofFullCopy(Blocks.SEAGRASS).setId(key).offsetType(BlockBehaviour.OffsetType.XZ)) }
    val TALL_RED_ALGAE = register("tall_red_algae") { key -> TallRedAlgaeBlock(Properties.ofFullCopy(Blocks.TALL_SEAGRASS).setId(key).offsetType(BlockBehaviour.OffsetType.NONE)) }

    val SEA_LETTUCE = register("sea_lettuce") { key -> SeaLettuceBlock(Properties.ofFullCopy(Blocks.SEAGRASS).setId(key).offsetType(BlockBehaviour.OffsetType.XZ)) }
    val TALL_SEA_LETTUCE = register("tall_sea_lettuce") { key -> TallSeaLettuceBlock(Properties.ofFullCopy(Blocks.TALL_SEAGRASS).setId(key).offsetType(BlockBehaviour.OffsetType.NONE)) }

    val BONE_WORMS = register("bone_worms") { key -> BoneWormsBlock(Properties.ofFullCopy(Blocks.SEAGRASS).setId(key)) }

    val DEAD_LOPHELIA_CORAL_BLOCK = register("dead_lophelia_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_LOPHELIA_CORAL_BLOCK = register("bleached_lophelia_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val LOPHELIA_CORAL_BLOCK = register("lophelia_coral_block") { key ->
        CoralBlock(DEAD_LOPHELIA_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_LOPHELIA_CORAL_FAN = register("dead_lophelia_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_LOPHELIA_CORAL_FAN = register("bleached_lophelia_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val LOPHELIA_CORAL_FAN = register("lophelia_coral_fan") { key -> CoralFanBlock(DEAD_LOPHELIA_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_LOPHELIA_CORAL = register("dead_lophelia_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_LOPHELIA_CORAL = register("bleached_lophelia_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val LOPHELIA_CORAL = register("lophelia_coral") { key -> CoralPlantBlock(DEAD_LOPHELIA_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_LOPHELIA_CORAL_WALL_FAN = register("dead_lophelia_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_LOPHELIA_CORAL_WALL_FAN = register("bleached_lophelia_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val LOPHELIA_CORAL_WALL_FAN = register("lophelia_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_LOPHELIA_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_BAMBOO_CORAL_BLOCK = register("dead_bamboo_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_BAMBOO_CORAL_BLOCK = register("bleached_bamboo_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BAMBOO_CORAL_BLOCK = register("bamboo_coral_block") { key ->
        CoralBlock(DEAD_BAMBOO_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_BAMBOO_CORAL_FAN = register("dead_bamboo_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_BAMBOO_CORAL_FAN = register("bleached_bamboo_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BAMBOO_CORAL_FAN = register("bamboo_coral_fan") { key -> CoralFanBlock(DEAD_BAMBOO_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_BAMBOO_CORAL = register("dead_bamboo_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_BAMBOO_CORAL = register("bleached_bamboo_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BAMBOO_CORAL = register("bamboo_coral") { key -> CoralPlantBlock(DEAD_BAMBOO_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_BAMBOO_CORAL_WALL_FAN = register("dead_bamboo_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_BAMBOO_CORAL_WALL_FAN = register("bleached_bamboo_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BAMBOO_CORAL_WALL_FAN = register("bamboo_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_BAMBOO_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_ROSE_CORAL_BLOCK = register("dead_rose_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_PINK))
    }

    val BLEACHED_ROSE_CORAL_BLOCK = register("bleached_rose_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val ROSE_CORAL_BLOCK = register("rose_coral_block") { key ->
        CoralBlock(
            DEAD_ROSE_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_PINK)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_ROSE_CORAL_FAN = register("dead_rose_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_ROSE_CORAL_FAN = register("bleached_rose_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val ROSE_CORAL_FAN = register("rose_coral_fan") { key -> CoralFanBlock(DEAD_ROSE_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_ROSE_CORAL = register("dead_rose_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_ROSE_CORAL = register("bleached_rose_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val ROSE_CORAL = register("rose_coral") { key -> CoralPlantBlock(DEAD_ROSE_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_ROSE_CORAL_WALL_FAN = register("dead_rose_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_ROSE_CORAL_WALL_FAN = register("bleached_rose_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val ROSE_CORAL_WALL_FAN = register("rose_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_ROSE_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_LEAF_CORAL_BLOCK = register("dead_leaf_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_GREEN))
    }

    val BLEACHED_LEAF_CORAL_BLOCK = register("bleached_leaf_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_GREEN))
    }

    val LEAF_CORAL_BLOCK = register("leaf_coral_block") { key ->
        CoralBlock(DEAD_LEAF_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_GREEN)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_LEAF_CORAL_FAN = register("dead_leaf_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_LEAF_CORAL_FAN = register("bleached_leaf_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val LEAF_CORAL_FAN = register("leaf_coral_fan") { key -> CoralFanBlock(DEAD_LEAF_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_LEAF_CORAL = register("dead_leaf_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_LEAF_CORAL = register("bleached_leaf_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val LEAF_CORAL = register("leaf_coral") { key -> CoralPlantBlock(DEAD_LEAF_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_LEAF_CORAL_WALL_FAN = register("dead_leaf_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_LEAF_CORAL_WALL_FAN = register("bleached_leaf_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val LEAF_CORAL_WALL_FAN = register("leaf_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_LEAF_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_SUN_CORAL_BLOCK = register("dead_sun_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_SUN_CORAL_BLOCK = register("bleached_sun_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val SUN_CORAL_BLOCK = register("sun_coral_block") { key ->
        CoralBlock(DEAD_SUN_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_ORANGE)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_SUN_CORAL_FAN = register("dead_sun_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_SUN_CORAL_FAN = register("bleached_sun_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val SUN_CORAL_FAN = register("sun_coral_fan") { key -> CoralFanBlock(DEAD_SUN_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_SUN_CORAL = register("dead_sun_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_SUN_CORAL = register("bleached_sun_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val SUN_CORAL = register("sun_coral") { key -> CoralPlantBlock(DEAD_SUN_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_SUN_CORAL_WALL_FAN = register("dead_sun_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_SUN_CORAL_WALL_FAN = register("bleached_sun_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val SUN_CORAL_WALL_FAN = register("sun_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_SUN_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_BUTTON_CORAL_BLOCK = register("dead_button_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_BUTTON_CORAL_BLOCK = register("bleached_button_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BUTTON_CORAL_BLOCK = register("button_coral_block") { key ->
        CoralBlock(DEAD_BUTTON_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_BUTTON_CORAL_FAN = register("dead_button_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_BUTTON_CORAL_FAN = register("bleached_button_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BUTTON_CORAL_FAN = register("button_coral_fan") { key -> CoralFanBlock(DEAD_BUTTON_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_BUTTON_CORAL = register("dead_button_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_BUTTON_CORAL = register("bleached_button_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BUTTON_CORAL = register("button_coral") { key -> CoralPlantBlock(DEAD_BUTTON_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_BUTTON_CORAL_WALL_FAN = register("dead_button_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_BUTTON_CORAL_WALL_FAN = register("bleached_button_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BUTTON_CORAL_WALL_FAN = register("button_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_BUTTON_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_ZIGZAG_CORAL_BLOCK = register("dead_zigzag_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_ZIGZAG_CORAL_BLOCK = register("bleached_zigzag_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val ZIGZAG_CORAL_BLOCK = register("zigzag_coral_block") { key ->
        CoralBlock(DEAD_ZIGZAG_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_ZIGZAG_CORAL_FAN = register("dead_zigzag_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_ZIGZAG_CORAL_FAN = register("bleached_zigzag_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val ZIGZAG_CORAL_FAN = register("zigzag_coral_fan") { key -> CoralFanBlock(DEAD_ZIGZAG_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_ZIGZAG_CORAL = register("dead_zigzag_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_ZIGZAG_CORAL = register("bleached_zigzag_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val ZIGZAG_CORAL = register("zigzag_coral") { key -> CoralPlantBlock(DEAD_ZIGZAG_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val DEAD_ZIGZAG_CORAL_WALL_FAN = register("dead_zigzag_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_ZIGZAG_CORAL_WALL_FAN = register("bleached_zigzag_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val ZIGZAG_CORAL_WALL_FAN = register("zigzag_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_ZIGZAG_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val DEAD_THORN_CORAL_BLOCK = register("dead_thorn_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_THORN_CORAL_BLOCK = register("bleached_thorn_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val THORN_CORAL_BLOCK = register("thorn_coral_block") { key ->
        CoralBlock(DEAD_THORN_CORAL_BLOCK.get(),
            Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK).setId(key)
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.CORAL_BLOCK))
    }

    val DEAD_THORN_CORAL_FAN = register("dead_thorn_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_THORN_CORAL_FAN = register("bleached_thorn_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val THORN_CORAL_FAN = register("thorn_coral_fan") { key -> CoralFanBlock(DEAD_THORN_CORAL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN).setId(key)) }
    val DEAD_THORN_CORAL = register("dead_thorn_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_THORN_CORAL = register("bleached_thorn_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val DEAD_THORN_CORAL_WALL_FAN = register("dead_thorn_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val BLEACHED_THORN_CORAL_WALL_FAN = register("bleached_thorn_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }
    val THORN_CORAL = register("thorn_coral") { key -> CoralPlantBlock(DEAD_THORN_CORAL.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL).setId(key)) }
    val THORN_CORAL_WALL_FAN = register("thorn_coral_wall_fan") { key -> CoralWallFanBlock(DEAD_THORN_CORAL_WALL_FAN.get(), Properties.ofFullCopy(Blocks.FIRE_CORAL_WALL_FAN).setId(key)) }

    val BLEACHED_FIRE_CORAL_BLOCK = register("bleached_fire_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_FIRE_CORAL_FAN = register("bleached_fire_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_FIRE_CORAL = register("bleached_fire_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_FIRE_CORAL_WALL_FAN = register("bleached_fire_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }

    val BLEACHED_TUBE_CORAL_BLOCK = register("bleached_tube_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_TUBE_CORAL_FAN = register("bleached_tube_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_TUBE_CORAL = register("bleached_tube_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_TUBE_CORAL_WALL_FAN = register("bleached_tube_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }

    val BLEACHED_HORN_CORAL_BLOCK = register("bleached_horn_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_HORN_CORAL_FAN = register("bleached_horn_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_HORN_CORAL = register("bleached_horn_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_HORN_CORAL_WALL_FAN = register("bleached_horn_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }

    val BLEACHED_BUBBLE_CORAL_BLOCK = register("bleached_bubble_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_BUBBLE_CORAL_FAN = register("bleached_bubble_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_BUBBLE_CORAL = register("bleached_bubble_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_BUBBLE_CORAL_WALL_FAN = register("bleached_bubble_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }

    val BLEACHED_BRAIN_CORAL_BLOCK = register("bleached_brain_coral_block") { key ->
        Block(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_BLOCK).setId(key)
            .mapColor(MapColor.TERRACOTTA_WHITE))
    }

    val BLEACHED_BRAIN_CORAL_FAN = register("bleached_brain_coral_fan") { key -> BaseCoralFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN).setId(key)) }
    val BLEACHED_BRAIN_CORAL = register("bleached_brain_coral") { key -> BaseCoralPlantBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL).setId(key)) }
    val BLEACHED_BRAIN_CORAL_WALL_FAN = register("bleached_brain_coral_wall_fan") { key -> BaseCoralWallFanBlock(Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_WALL_FAN).setId(key)) }


    val GLOWSTICK = register("glowstick") { key ->
        GlowstickBlock(Properties.ofFullCopy(Blocks.TORCH).setId(key)
            .noCollision()
            .lightLevel { 12 }
            .noOcclusion())
    }

    val WALL_GLOWSTICK = register("wall_glowstick") { key ->
        WallGlowstickBlock(Properties.ofFullCopy(Blocks.WALL_TORCH).setId(key)
            .overrideLootTable(GLOWSTICK.get().lootTable)
            .noCollision()
            .lightLevel { 12 }
            .noOcclusion())
    }

    val THERMAL_VENT = register("hydrothermal_vent_shaft") { key ->
        ThermalVentBlock(2,
            Properties.ofFullCopy(Blocks.TUFF).setId(key)
            .noOcclusion()
            .strength(0.5F)
            .pushReaction(PushReaction.POPPED))
    }

    val GIANT_THERMAL_VENT = register("giant_thermal_vent") { key ->
        GiantThermalVentBlock(2,
            Properties.ofFullCopy(Blocks.TUFF).setId(key)
            .strength(0.5F)
            .pushReaction(PushReaction.PUSH_PULL))
    }

    val TUBE_WORM = register("tube_worm") { key ->
        TubeWormBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_GREEN)
            .sound(SoundType.SLIME_BLOCK)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED))
    }

    val TUBE_SPONGE = register("tube_sponge") { key ->
        LivingSpongeBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_YELLOW)
            .sound(SoundType.SLIME_BLOCK)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED))
    }

    val GLASS_SPONGE = register("glass_sponge") { key ->
        LivingSpongeBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .sound(SoundType.SLIME_BLOCK)
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
        )
    }

    val HARP_SPONGE = register("harp_sponge") { key ->
        LivingSpongeBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .sound(SoundType.SLIME_BLOCK)
            .noCollision()
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
        )
    }

    val PING_PONG_SPONGE = register("ping_pong_sponge") { key ->
        LivingSpongeBlock(Properties.of().setId(key)
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .sound(SoundType.SLIME_BLOCK)
            .noCollision()
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
        )
    }

    val GIANT_CLAM = register("giant_clam") { key ->
        GiantClamBlock(true, Properties.ofFullCopy(Blocks.TUFF).setId(key)
            .noOcclusion()
            .strength(1.0F)
            .pushReaction(PushReaction.POPPED))
    }

    val OYSTER = register("oyster_block") { key ->
        OysterBlock(true, Properties.ofFullCopy(Blocks.TUFF).setId(key)
            .noOcclusion()
            .strength(1.0F)
            .pushReaction(PushReaction.POPPED))
    }

    val BUOY = register("buoy") { key ->
        BuoyBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(key)
            .noOcclusion()
            .noTerrainParticles()
            .lightLevel { 12 })
    }

    val BELL_BUOY = register("bell_buoy") { key ->
        BellBuoyBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(key)
            .noOcclusion()
            .noTerrainParticles())
    }

    val DECORATIVE_BUBBLE_COLUMN = register("decorative_bubble_column") { key ->
        DecorativeBubbleColumnBlock(Properties.ofFullCopy(Blocks.BUBBLE_COLUMN).setId(key))
    }

    val BUBBLE_NET = register("bubble_net") { key ->
        BubbleNetBlock(Properties.ofFullCopy(Blocks.BUBBLE_COLUMN).setId(key))
    }

    private fun createPlushieBlock(key: net.minecraft.resources.ResourceKey<Block>, variant: PlushieBlock.Variant): PlushieBlock {
        return PlushieBlock(
            variant,
            Properties.of().setId(key).instabreak().pushReaction(PushReaction.POPPED).sound(SoundType.WOOL)
                .instrument(NoteBlockInstrument.CUSTOM_HEAD)
                .noTerrainParticles()
        )
    }

    fun <T : Block> register(id: String, block: (net.minecraft.resources.ResourceKey<Block>) -> T): RegistryObject<Block> {
        return CommonClass.BLOCKS.register(id) { block(net.minecraft.resources.ResourceKey.create(
            net.minecraft.core.registries.Registries.BLOCK, CommonClass.locate(id))) }
    }

    /**
     * Adds [addedBlocks] to the supported blocks list of a block entity type.
     */
    fun <T : BlockEntity> BlockEntityType<T>.addBlocks(vararg addedBlocks: Block) {
        val extension = this as net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityType
        addedBlocks.forEach(extension::addValidBlock)
    }
}
