package dev.hybridlabs.aquatic.client.render.entity.mammal.layer

import dev.hybridlabs.aquatic.client.model.entity.mammal.OrcaEntityModel
import dev.hybridlabs.aquatic.entity.mammal.OrcaEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseDolphinEntityRenderer
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class OrcaEyeSpotEntityLayer(
    renderer: BaseDolphinEntityRenderer<OrcaEntity>
) : HybridTextureLayer<OrcaEntity, LivingEntityRenderState>(renderer) {

    private fun getEyeSpotTexture(animatable: OrcaEntity, layer: String): Identifier {
        return (geoModel as OrcaEntityModel).getEyeSpotTextureResource(animatable, layer)
    }

    override fun addRenderData(animatable: OrcaEntity, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable.getEyeSpotTextureName().isEmpty()) return

        val eyeSpotTexture = getEyeSpotTexture(animatable, animatable.getEyeSpotTextureName())
        val eyeSpotRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(eyeSpotTexture)

        setLayerData(renderState, eyeSpotRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}