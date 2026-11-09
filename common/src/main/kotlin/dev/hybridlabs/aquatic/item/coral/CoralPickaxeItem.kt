package dev.hybridlabs.aquatic.item.coral

import dev.hybridlabs.hapi.item.HAPIToolMaterials
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class CoralPickaxeItem(settings: Properties) : Item(settings.pickaxe(HAPIToolMaterials.CORAL, 1.0f, -2.8f)) {

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: net.minecraft.world.item.component.TooltipDisplay,
        tooltip: java.util.function.Consumer<Component>,
        options: TooltipFlag
    ) {
        val text = Component.translatable(this.descriptionId.plus(".description")).withStyle(ChatFormatting.GRAY)

        tooltip.accept(text)
        super.appendHoverText(stack, context, display, tooltip, options)
    }
}