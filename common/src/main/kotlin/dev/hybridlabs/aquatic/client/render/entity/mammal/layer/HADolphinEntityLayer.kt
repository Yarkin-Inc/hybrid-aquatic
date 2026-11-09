package dev.hybridlabs.aquatic.client.render.entity.mammal.layer

import dev.hybridlabs.aquatic.entity.feature.OverlayTextureFeature
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseDolphinEntityModel
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseDolphinEntityRenderer
import dev.hybridlabs.hapi.entity.base.aquatic.BaseDolphinEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class HADolphinEntityLayer<T: BaseDolphinEntity>(
    renderer: BaseDolphinEntityRenderer<T>
) : HybridTextureLayer<T, LivingEntityRenderState>(renderer) {

    private fun getLayerTextureResource(animatable: T, layer: String): Identifier {
        return (geoModel as BaseDolphinEntityModel<T>).getLayerTextureResource(animatable, layer.lowercase())
    }

    override fun addRenderData(animatable: T, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable !is OverlayTextureFeature) return
        if (animatable.getOverlayTextureName().isEmpty()) return

        val layerTexture: Identifier = getLayerTextureResource(animatable, animatable.getOverlayTextureName())
        val layerRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(layerTexture)

        setLayerData(renderState, layerRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}