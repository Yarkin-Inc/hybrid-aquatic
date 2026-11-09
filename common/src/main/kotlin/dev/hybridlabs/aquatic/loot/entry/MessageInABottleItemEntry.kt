package dev.hybridlabs.aquatic.loot.entry

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.hybridlabs.aquatic.block.MessageInABottleBlock
import dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity
import dev.hybridlabs.aquatic.item.HAItems
import dev.hybridlabs.aquatic.registry.HARegistryKeys
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.core.Holder
import java.util.Optional
import net.minecraft.world.level.storage.loot.entries.SingleEntryContainerBase
import net.minecraft.world.level.storage.loot.functions.LootItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import java.util.function.Consumer

class MessageInABottleItemEntry(
    weight: Int,
    quality: Int,
    conditions: Optional<Holder<LootItemCondition>>,
    functions: Optional<Holder<LootItemFunction>>
) : SingleEntryContainerBase(weight, quality, conditions, functions) {
    override fun codec(): MapCodec<MessageInABottleItemEntry> = CODEC

    public override fun createItemStack(consumer: Consumer<ItemStack>, context: LootContext) {
        val random = context.random
        val stack = ItemStack(HAItems.MESSAGE_IN_A_BOTTLE.get())
        val stuff = CompoundTag().apply {
            val variants = MessageInABottleBlock.Variant.entries
            putString(MessageInABottleBlockEntity.VARIANT_KEY, variants[random.nextInt(variants.size)].id)
        }
        stack.set(DataComponents.BLOCK_ENTITY_DATA, net.minecraft.world.item.component.TypedEntityData.of(
            dev.hybridlabs.aquatic.block.entity.HABlockEntityTypes.MESSAGE_IN_A_BOTTLE.get(), stuff))
        consumer.accept(stack)
    }

    companion object {
        val CODEC: MapCodec<MessageInABottleItemEntry> = RecordCodecBuilder.mapCodec {
                inst -> uniformFields(inst).apply(inst, ::MessageInABottleItemEntry)
        }

        fun builder(): Builder<*> {
            return simpleBuilder(::MessageInABottleItemEntry)
        }
    }
}