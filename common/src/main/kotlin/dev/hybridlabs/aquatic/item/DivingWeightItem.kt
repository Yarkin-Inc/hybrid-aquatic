package dev.hybridlabs.aquatic.item

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class DivingWeightItem(settings: Properties) : Item(settings) {

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: net.minecraft.world.item.component.TooltipDisplay,
        lines: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val text = Component.translatable(this.descriptionId.plus(".description")).withStyle(ChatFormatting.GRAY)

        lines.accept(text)
        super.appendHoverText(stack, context, display, lines, tooltipFlag)
    }
}