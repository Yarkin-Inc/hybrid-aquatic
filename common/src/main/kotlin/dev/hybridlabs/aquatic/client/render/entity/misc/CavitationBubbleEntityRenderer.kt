package dev.hybridlabs.aquatic.client.render.entity.misc

import dev.hybridlabs.aquatic.client.model.entity.misc.CavitationBubbleEntityModel
import dev.hybridlabs.aquatic.entity.projectile.CavitationBubbleEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider
import dev.hybridlabs.hapi.client.render.HybridGeoObjectEntityRenderer as GeoEntityRenderer
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer

class CavitationBubbleEntityRenderer(
    context: EntityRendererProvider.Context
) : GeoEntityRenderer<CavitationBubbleEntity>(context, CavitationBubbleEntityModel()) {

    init {
        withRenderLayer(AutoGlowingGeoLayer(this))
        this.shadowRadius = 0.5f
    }

    override fun getMotionAnimThreshold(animatable: CavitationBubbleEntity): Float {
        return 0.0025f
    }
}