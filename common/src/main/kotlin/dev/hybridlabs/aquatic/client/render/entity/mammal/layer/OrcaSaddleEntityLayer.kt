package dev.hybridlabs.aquatic.client.render.entity.mammal.layer

import dev.hybridlabs.aquatic.client.model.entity.mammal.OrcaEntityModel
import dev.hybridlabs.aquatic.entity.mammal.OrcaEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseDolphinEntityRenderer
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class OrcaSaddleEntityLayer(
    renderer: BaseDolphinEntityRenderer<OrcaEntity>
) : HybridTextureLayer<OrcaEntity, LivingEntityRenderState>(renderer) {

    private fun getSaddleTexture(animatable: OrcaEntity, layer: String): Identifier {
        return (geoModel as OrcaEntityModel).getSaddleTextureResource(animatable, layer)
    }

    override fun addRenderData(animatable: OrcaEntity, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable.getSaddleTextureName().isEmpty()) return

        val saddleTexture = getSaddleTexture(animatable, animatable.getSaddleTextureName())
        val saddleRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(saddleTexture)

        setLayerData(renderState, saddleRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}