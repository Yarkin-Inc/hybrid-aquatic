package dev.hybridlabs.aquatic.client.render.entity.misc.layer

import dev.hybridlabs.aquatic.client.model.entity.misc.ArgonautEntityModel
import dev.hybridlabs.aquatic.client.render.entity.misc.ArgonautEntityRenderer
import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.render.HybridTextureLayer
import net.minecraft.client.renderer.entity.state.EntityRenderState

class ArgonautVisorEntityLayer<T: ArgonautEntity>(
    renderer: ArgonautEntityRenderer<T>
): HybridTextureLayer<T, EntityRenderState>(renderer) {

    private fun getVisorTexture(): Identifier {
        return (geoModel as ArgonautEntityModel<ArgonautEntity> ).getVisorTextureResource()
    }

    override fun addRenderData(animatable: T, related: Void?, renderState: EntityRenderState, partialTick: Float) {
        super.addRenderData(animatable, related, renderState, partialTick)
        val visorTexture = getVisorTexture()
        val visorRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucentCull(visorTexture)

        setLayerData(renderState, visorRenderType, -1, OverlayTexture.NO_OVERLAY)
    }
}