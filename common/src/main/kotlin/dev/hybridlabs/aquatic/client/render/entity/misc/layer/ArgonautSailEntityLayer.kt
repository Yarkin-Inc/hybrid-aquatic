package dev.hybridlabs.aquatic.client.render.entity.misc.layer

import dev.hybridlabs.aquatic.client.model.entity.misc.ArgonautEntityModel
import dev.hybridlabs.aquatic.client.render.entity.misc.ArgonautEntityRenderer
import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.EntityRenderState

class ArgonautSailEntityLayer<T: ArgonautEntity>(
    renderer: ArgonautEntityRenderer<T>
): HybridTextureLayer<T, EntityRenderState>(renderer) {

    private fun getSailTexture(animatable: T): Identifier {
        return (geoModel as ArgonautEntityModel<ArgonautEntity> ).getSailTextureResource(animatable)
    }

    override fun addRenderData(animatable: T, related: Void?, renderState: EntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        val sailTexture = getSailTexture(animatable)
        val sailRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(sailTexture)
        if (animatable.getSailColor() == ArgonautEntity.SailColor.NONE) return

        setLayerData(renderState, sailRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}