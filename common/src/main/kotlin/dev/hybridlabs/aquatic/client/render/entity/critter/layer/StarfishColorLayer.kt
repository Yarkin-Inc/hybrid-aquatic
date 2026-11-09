package dev.hybridlabs.aquatic.client.render.entity.critter.layer

import dev.hybridlabs.aquatic.client.model.entity.critter.StarfishEntityModel
import dev.hybridlabs.aquatic.client.render.entity.critter.StarfishEntityRenderer
import dev.hybridlabs.aquatic.entity.critter.StarfishEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState


class StarfishColorLayer(
    renderer: StarfishEntityRenderer,
) : HybridTextureLayer<StarfishEntity, LivingEntityRenderState>(renderer) {


    private fun getLayerTextureResource(animatable: StarfishEntity): Identifier {
        return (geoModel as StarfishEntityModel).getTextureResource(animatable)
    }

    override fun addRenderData(animatable: StarfishEntity, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable.variant != StarfishEntity.Companion.Type.SMALL &&
            animatable.variant != StarfishEntity.Companion.Type.MEDIUM) {
            return
        }

        val layerTexture: Identifier = getLayerTextureResource(animatable)
        val tintRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(layerTexture)

        val color = net.minecraft.util.ARGB.opaque(animatable.starfishColor)

        setLayerData(renderState, tintRenderType, color, LivingEntityRenderer.getOverlayCoords(renderState, 0.0f))
    }

}