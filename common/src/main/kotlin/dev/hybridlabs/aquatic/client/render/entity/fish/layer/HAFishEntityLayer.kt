package dev.hybridlabs.aquatic.client.render.entity.fish.layer

import dev.hybridlabs.aquatic.entity.feature.OverlayTextureFeature
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseFishEntityRenderer
import dev.hybridlabs.hapi.entity.base.aquatic.BaseFishEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class HAFishEntityLayer<T: BaseFishEntity>(
    renderer: BaseFishEntityRenderer<T>
) : HybridTextureLayer<T, LivingEntityRenderState>(renderer) {

    private fun getLayerTextureResource(layer: String): Identifier {
        return (geoModel as BaseFishEntityModel<*>).getLayerTextureResource(layer.lowercase())
    }

    override fun addRenderData(animatable: T, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable !is OverlayTextureFeature) return
        if (animatable.getOverlayTextureName().isEmpty()) return

        val layerTexture: Identifier = getLayerTextureResource(animatable.getOverlayTextureName())
        val layerRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(layerTexture)

        setLayerData(renderState, layerRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}