package dev.hybridlabs.aquatic.client.render.entity.cephalopods.layer

import dev.hybridlabs.aquatic.entity.cephalopod.OctopusEntity
import dev.hybridlabs.aquatic.entity.feature.OverlayTextureFeature
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseOctopusEntityModel
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseOctopusEntityRenderer
import dev.hybridlabs.hapi.entity.base.aquatic.BaseOctopusEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState


class HAOctopusEntityLayer<T: BaseOctopusEntity>(
    renderer: BaseOctopusEntityRenderer<T>,
) : HybridTextureLayer<T, LivingEntityRenderState>(renderer) {

    private fun getLayerTextureResource(): Identifier {
        return (geoModel as BaseOctopusEntityModel<*>).getLayerTextureResource()
    }

    override fun addRenderData(animatable: T, related: Void?, renderState: LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        if (animatable !is OverlayTextureFeature) return

        if (animatable is OctopusEntity &&
            animatable.variant != OctopusEntity.Companion.Type.OCTOPUS) {
            return
        }

        if (!animatable.isSitting()) return

        val overlayName = animatable.getOverlayTextureName()
        if (overlayName.isEmpty()) return

        val layerTexture = getLayerTextureResource()
        val tintRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(layerTexture)

        var current = net.minecraft.util.ARGB.opaque(animatable.getCurrentColor())
        val target = net.minecraft.util.ARGB.opaque(animatable.getTargetColor())

        if (current != target) {
            val blendSpeed = 0.025f
            current = net.minecraft.util.ARGB.srgbLerp(blendSpeed, current, target)
            animatable.setCurrentColor(current)
        }

        setLayerData(renderState, tintRenderType, net.minecraft.util.ARGB.scaleRGB(current, 1.2f), LivingEntityRenderer.getOverlayCoords(renderState, 0.0f))
    }
}