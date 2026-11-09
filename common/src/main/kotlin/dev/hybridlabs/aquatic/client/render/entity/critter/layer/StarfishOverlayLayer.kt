package dev.hybridlabs.aquatic.client.render.entity.critter.layer

import dev.hybridlabs.aquatic.client.render.entity.critter.StarfishEntityRenderer
import dev.hybridlabs.aquatic.entity.critter.StarfishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseCritterEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState


class StarfishOverlayLayer(
    renderer: StarfishEntityRenderer,
) : HybridTextureLayer<StarfishEntity, LivingEntityRenderState>(renderer) {


    private fun getLayerTextureResource(layer: String): Identifier {
        return (geoModel as BaseCritterEntityModel<*>).getLayerTextureResource(layer.lowercase())
    }

    override fun addRenderData(animatable: StarfishEntity, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        val overlayName = animatable.getOverlayTextureName()
        if (overlayName.isEmpty()) return

        val layerTexture: Identifier = getLayerTextureResource(animatable.getOverlayTextureName())
        val tintRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(layerTexture)

        val color = net.minecraft.util.ARGB.opaque(animatable.overlayColor)

        setLayerData(renderState, tintRenderType, color, LivingEntityRenderer.getOverlayCoords(renderState, 0.0f))
    }

}