package dev.hybridlabs.aquatic.client.render.entity.fish.layer

import dev.hybridlabs.aquatic.client.model.entity.fish.CarpEntityModel
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseFishEntityRenderer
import dev.hybridlabs.aquatic.entity.fish.CarpEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class CarpPatternEntityLayer(
    renderer: BaseFishEntityRenderer<CarpEntity>
) : HybridTextureLayer<CarpEntity, LivingEntityRenderState>(renderer) {

    private fun getPatternTexture(animatable: CarpEntity, layer: String): Identifier {
        return (geoModel as CarpEntityModel).getPatternTextureResource(animatable, layer)
    }

    override fun addRenderData(animatable: CarpEntity, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable.getPatternTextureName().isEmpty()) return
        if (animatable.isBaby) return

        val patternTexture = getPatternTexture(animatable, animatable.getPatternTextureName())
        val patternRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(patternTexture)

        setLayerData(renderState, patternRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}