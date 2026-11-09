package dev.hybridlabs.aquatic.client.render.item

import dev.hybridlabs.aquatic.item.GiantGreenAnemoneBlockItem
import dev.hybridlabs.aquatic.client.model.block.entity.GiantGreenAnemoneBlockEntityModel
import com.geckolib.model.GeoModel
import com.geckolib.renderer.GeoItemRenderer
import com.geckolib.renderer.base.GeoRenderState

class GiantGreenAnemoneBlockItemRenderer : GeoItemRenderer<GiantGreenAnemoneBlockItem>(object : GeoModel<GiantGreenAnemoneBlockItem>() {
    override fun getModelResource(state: GeoRenderState) = GiantGreenAnemoneBlockEntityModel.MODEL_LOCATION
    override fun getTextureResource(state: GeoRenderState) = GiantGreenAnemoneBlockEntityModel.TEXTURE_LOCATION
    override fun getAnimationResource(item: GiantGreenAnemoneBlockItem) = GiantGreenAnemoneBlockEntityModel.ANIMATION_LOCATION
})
