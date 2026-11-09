package dev.hybridlabs.aquatic.item

import dev.hybridlabs.aquatic.entity.HAEntityTypes
import dev.hybridlabs.hapi.tag.HAPIBiomeTags
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import dev.hybridlabs.aquatic.item.instrument.HAInstruments
import net.minecraft.world.item.component.InstrumentComponent
import net.minecraft.world.item.component.TooltipDisplay
import java.util.function.Consumer
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.core.BlockPos
import net.minecraft.server.MinecraftServer
import java.util.WeakHashMap
import net.minecraft.tags.TagKey
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Instrument
import net.minecraft.world.item.InstrumentItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.Level

class OminousConchItem(
    properties: Properties,
    instruments: TagKey<Instrument>
) : InstrumentItem(properties.delayedComponent(DataComponents.INSTRUMENT) { registries ->
    InstrumentComponent(registries.lookupOrThrow(net.minecraft.core.registries.Registries.INSTRUMENT)
        .getOrThrow(HAInstruments.OMINOUS_CONCH_HORN))
}) {

    companion object {
        private const val TAG_HAS_SUMMONED = "hasSummoned"
        private const val SUMMON_DELAY_TICKS = 140
        private data class PendingSummon(val level: ServerLevel, val pos: BlockPos, val dueTick: Long)
        private val pendingSummons = WeakHashMap<MinecraftServer, MutableList<PendingSummon>>()

        fun tickSummons(server: MinecraftServer) {
            val pending = pendingSummons[server] ?: return
            val due = pending.filter { it.level.gameTime >= it.dueTick }
            pending.removeAll(due.toSet())
            if (pending.isEmpty()) pendingSummons.remove(server)
            for (summon in due) {
                (HAItems.OMINOUS_CONCH.get() as OminousConchItem).spawnSummon(summon.level, summon.pos)
            }
        }

        fun clearSummons(server: MinecraftServer) { pendingSummons.remove(server) }
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: TooltipDisplay,
        lines: Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        lines.accept(
            Component.translatable("item.hybrid_aquatic.ominous_conch.function")
                .withStyle(ChatFormatting.GRAY)
        )

        val hasSummoned = stack.get(DataComponents.CUSTOM_DATA)
            ?.copyTag()?.getBooleanOr(TAG_HAS_SUMMONED, false)
            ?: false

        if (!hasSummoned) {
            lines.accept(
                Component.translatable("tooltip.hybrid_aquatic.ominous_conch.unused")
                    .withStyle(ChatFormatting.DARK_PURPLE)
            )
        } else {
            lines.accept(
                Component.translatable("tooltip.hybrid_aquatic.ominous_conch.used")
                    .withStyle(ChatFormatting.GRAY)
            )
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        val result = super.use(level, player, hand)
        val stack = player.getItemInHand(hand)

        if (level.isClientSide || !result.consumesAction()) return result

        val tag = stack.get(DataComponents.CUSTOM_DATA)?.copyTag()

        if (tag != null && tag.getBooleanOr(TAG_HAS_SUMMONED, false)) return result

        val biome = level.getBiome(player.blockPosition())
        if (!biome.`is`(HAPIBiomeTags.CAN_SUMMON_SHELL_BEAST)) return result
        if (!player.isUnderWater) return result

        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY) { data ->
            data.update { tag ->
                tag.putBoolean(TAG_HAS_SUMMONED, true)
            }
        }

        val serverLevel = level as ServerLevel
        val pos = player.blockPosition()

        pendingSummons.getOrPut(serverLevel.server) { mutableListOf() }.add(
            PendingSummon(serverLevel, pos, serverLevel.gameTime + SUMMON_DELAY_TICKS))

        return result
    }

    private fun spawnSummon(level: ServerLevel, pos: BlockPos) {
            val shellBeast = HAEntityTypes.SHELL_BEAST.get().create(level, EntitySpawnReason.MOB_SUMMONED)

            if (shellBeast != null) {

                shellBeast.snapTo(
                    pos.x + 0.5,
                    pos.y.toDouble(),
                    pos.z + 0.5,
                    0f,
                    0f
                )

                level.addFreshEntity(shellBeast)

                val random = level.random

                for (i in 0 until 6) {

                    val offsetX = (random.nextDouble() - 0.5) * 8.0
                    val offsetZ = (random.nextDouble() - 0.5) * 8.0

                    val beakling = HAEntityTypes.BEAKLING.get().create(level, EntitySpawnReason.MOB_SUMMONED)

                    if (beakling != null) {

                        beakling.snapTo(
                            shellBeast.x + offsetX,
                            shellBeast.y,
                            shellBeast.z + offsetZ,
                            random.nextFloat() * 360f,
                            0f
                        )

                        beakling.setOwner(shellBeast)

                        level.addFreshEntity(beakling)
                    }
                }
            }

    }
}
