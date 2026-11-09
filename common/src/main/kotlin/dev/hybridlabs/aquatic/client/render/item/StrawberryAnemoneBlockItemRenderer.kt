package dev.hybridlabs.aquatic.client.render.item

import dev.hybridlabs.aquatic.item.StrawberryAnemoneBlockItem
import dev.hybridlabs.aquatic.client.model.block.entity.StrawberryAnemoneBlockEntityModel
import com.geckolib.model.GeoModel
import com.geckolib.renderer.GeoItemRenderer
import com.geckolib.renderer.base.GeoRenderState

class StrawberryAnemoneBlockItemRenderer : GeoItemRenderer<StrawberryAnemoneBlockItem>(object : GeoModel<StrawberryAnemoneBlockItem>() {
    override fun getModelResource(state: GeoRenderState) = StrawberryAnemoneBlockEntityModel.MODEL_LOCATION
    override fun getTextureResource(state: GeoRenderState) = StrawberryAnemoneBlockEntityModel.TEXTURE_LOCATION
    override fun getAnimationResource(item: StrawberryAnemoneBlockItem) = StrawberryAnemoneBlockEntityModel.ANIMATION_LOCATION
})
