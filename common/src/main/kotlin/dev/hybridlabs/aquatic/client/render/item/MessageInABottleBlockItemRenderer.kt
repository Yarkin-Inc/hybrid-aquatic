package dev.hybridlabs.aquatic.client.render.item

import com.geckolib.constant.dataticket.DataTicket
import com.geckolib.model.GeoModel
import com.geckolib.renderer.GeoItemRenderer
import com.geckolib.renderer.base.GeoRenderState
import dev.hybridlabs.aquatic.block.MessageInABottleBlock.Variant
import dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity
import dev.hybridlabs.aquatic.client.model.block.entity.MessageInABottleBlockEntityModel
import dev.hybridlabs.aquatic.item.MessageInABottleItem
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.Identifier
import net.minecraft.client.renderer.rendertype.RenderTypes

class MessageInABottleBlockItemRenderer : GeoItemRenderer<MessageInABottleItem>(BottleItemModel()) {
    override fun getRenderType(state: GeoRenderState, texture: Identifier) = RenderTypes.entityTranslucent(texture)
}

private class BottleItemModel : GeoModel<MessageInABottleItem>() {
    private val variantTicket = DataTicket.create("ha_bottle_item_variant", Variant::class.java)
    override fun addAdditionalStateData(item: MessageInABottleItem, related: Any?, state: GeoRenderState) {
        val stack = (related as? GeoItemRenderer.RenderData)?.itemStack()
        val name = stack?.get(DataComponents.BLOCK_ENTITY_DATA)?.copyTagWithoutId()
            ?.getStringOr(MessageInABottleBlockEntity.VARIANT_KEY, "bottle") ?: "bottle"
        state.addGeckolibData(variantTicket, Variant.byId(name))
    }
    override fun getModelResource(state: GeoRenderState) =
        MessageInABottleBlockEntityModel.VARIANT_MODELS.getValue(state.getGeckolibData(variantTicket) ?: Variant.BOTTLE)
    override fun getTextureResource(state: GeoRenderState) =
        MessageInABottleBlockEntityModel.VARIANT_TEXTURES.getValue(state.getGeckolibData(variantTicket) ?: Variant.BOTTLE)
    override fun getAnimationResource(item: MessageInABottleItem) = MessageInABottleBlockEntityModel.WATER_BOB_ANIMATION_ID
}
