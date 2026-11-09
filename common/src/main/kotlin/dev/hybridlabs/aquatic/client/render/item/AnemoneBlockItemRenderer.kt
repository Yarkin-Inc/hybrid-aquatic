package dev.hybridlabs.aquatic.client.render.item

import dev.hybridlabs.aquatic.item.AnemoneBlockItem
import dev.hybridlabs.aquatic.client.model.block.entity.AnemoneBlockEntityModel
import com.geckolib.model.GeoModel
import com.geckolib.renderer.GeoItemRenderer
import com.geckolib.renderer.base.GeoRenderState

class AnemoneBlockItemRenderer : GeoItemRenderer<AnemoneBlockItem>(object : GeoModel<AnemoneBlockItem>() {
    override fun getModelResource(state: GeoRenderState) = AnemoneBlockEntityModel.MODEL_LOCATION
    override fun getTextureResource(state: GeoRenderState) = AnemoneBlockEntityModel.TEXTURE_LOCATION
    override fun getAnimationResource(item: AnemoneBlockItem) = AnemoneBlockEntityModel.ANIMATION_LOCATION
})
