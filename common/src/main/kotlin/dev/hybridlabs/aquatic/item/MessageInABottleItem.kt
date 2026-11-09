package dev.hybridlabs.aquatic.item

import dev.hybridlabs.aquatic.block.HABlocks
import dev.hybridlabs.aquatic.block.MessageInABottleBlock
import dev.hybridlabs.aquatic.block.MessageInABottleBlock.Variant
import dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag


/**
 * Represents the Message in a Bottle block item.
 * @see MessageInABottleBlock
 */
class MessageInABottleItem(settings: Properties) : PlaceableInWaterItem(HABlocks.MESSAGE_IN_A_BOTTLE.get(), settings), com.geckolib.animatable.GeoItem {
    private val cache = com.geckolib.util.GeckoLibUtil.createInstanceCache(this)
    override fun getAnimatableInstanceCache() = cache
    override fun registerControllers(controllers: com.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar) {}
    override fun createGeoRenderer(consumer: java.util.function.Consumer<com.geckolib.animatable.client.GeoRenderProvider>) {
        consumer.accept(object : com.geckolib.animatable.client.GeoRenderProvider {
            private val renderer by lazy { dev.hybridlabs.aquatic.client.render.item.MessageInABottleBlockItemRenderer() }
            override fun getGeoItemRenderer() = renderer
        })
    }


    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: net.minecraft.world.item.component.TooltipDisplay,
        tooltipComponents: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(stack, context, display, tooltipComponents, tooltipFlag)
        // custom variant translation keys
        val id = stack.components[DataComponents.BLOCK_ENTITY_DATA]?.copyTagWithoutId()?.getStringOr(MessageInABottleBlockEntity.VARIANT_KEY, "bottle") ?: ""
        val variant = Variant.byId(id)
        tooltipComponents.accept(Component.translatable("$descriptionId.variant.${variant.id}").withStyle(ChatFormatting.GRAY))
        tooltipComponents.accept(Component.translatable("$descriptionId.change_hint"))
    }
}
