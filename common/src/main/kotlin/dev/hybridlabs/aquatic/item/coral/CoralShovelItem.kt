package dev.hybridlabs.aquatic.item.coral

import dev.hybridlabs.hapi.item.HAPIToolMaterials
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item
import net.minecraft.world.item.TooltipFlag

class CoralShovelItem(settings: Properties) : Item(settings.shovel(HAPIToolMaterials.CORAL, 1.5f, -3.0f)) {

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