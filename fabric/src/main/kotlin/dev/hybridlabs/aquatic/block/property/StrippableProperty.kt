package dev.hybridlabs.aquatic.block.property

import dev.hybridlabs.aquatic.block.HAPlatformBlocks
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.ItemTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.gameevent.GameEvent

/** Extends axe stripping without replacing the data-driven vanilla axe transformer. */
object StrippableProperty {
    private val stripped = mutableMapOf<Block, Block>()
    init {
        register(HAPlatformBlocks.DRIFTWOOD_LOG.get(), HAPlatformBlocks.STRIPPED_DRIFTWOOD_LOG.get())
        register(HAPlatformBlocks.DRIFTWOOD_WOOD.get(), HAPlatformBlocks.STRIPPED_DRIFTWOOD_WOOD.get())
        UseBlockCallback.EVENT.register { player, world, hand, hit ->
            val stack = player.getItemInHand(hand)
            val old = world.getBlockState(hit.blockPos)
            val replacement = stripped[old.block]
            if (player.isSpectator || replacement == null || !stack.`is`(ItemTags.AXES) ||
                !player.mayUseItemAt(hit.blockPos, hit.direction, stack)) InteractionResult.PASS
            else {
                if (!world.isClientSide) {
                    world.setBlock(hit.blockPos, replacement.withPropertiesOf(old), 11)
                    world.playSound(null, hit.blockPos, SoundEvents.AXE_STRIP.value(), SoundSource.BLOCKS, 1f, 1f)
                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, hit.blockPos)
                    stack.hurtAndBreak(1, player, if (hand == net.minecraft.world.InteractionHand.MAIN_HAND) EquipmentSlot.MAINHAND else EquipmentSlot.OFFHAND)
                }
                InteractionResult.SUCCESS
            }
        }
    }

    fun register(input: Block, output: Block) { stripped[input] = output }
}
