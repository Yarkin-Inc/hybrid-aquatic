package dev.hybridlabs.aquatic.item

import dev.hybridlabs.aquatic.block.CrateBlock
import dev.hybridlabs.aquatic.block.PlushieBlock
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.*
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.level.block.Block
import java.util.function.Consumer

class DescriptionBlockItem(block: Block, properties: Properties) : BlockItem(block, properties.overrideDescription(block.descriptionId)) {
    override fun appendHoverText(stack: ItemStack, context: TooltipContext, display: TooltipDisplay,
                                 tooltip: Consumer<Component>, flags: TooltipFlag) {
        super.appendHoverText(stack, context, display, tooltip, flags)
        if (block is CrateBlock || block is PlushieBlock)
            tooltip.accept(Component.translatable(block.descriptionId + ".description").withStyle(ChatFormatting.GRAY))
    }
}
