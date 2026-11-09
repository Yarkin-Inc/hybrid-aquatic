package dev.hybridlabs.aquatic.client.render.entity.misc

import dev.hybridlabs.aquatic.client.model.entity.misc.ArgonautEntityModel
import dev.hybridlabs.aquatic.client.render.entity.misc.layer.ArgonautSailEntityLayer
import dev.hybridlabs.aquatic.client.render.entity.misc.layer.ArgonautShellEntityLayer
import dev.hybridlabs.aquatic.client.render.entity.misc.layer.ArgonautVisorEntityLayer
import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider
import dev.hybridlabs.hapi.client.render.HybridGeoObjectEntityRenderer as GeoEntityRenderer
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer

class ArgonautEntityRenderer<T : ArgonautEntity>(
    context: EntityRendererProvider.Context
) : GeoEntityRenderer<T>(context, ArgonautEntityModel()) {

    init {
        withRenderLayer(ArgonautVisorEntityLayer(this))
        withRenderLayer(ArgonautSailEntityLayer(this))
        withRenderLayer(ArgonautShellEntityLayer(this))
        withRenderLayer(AutoGlowingGeoLayer(this))
        this.shadowRadius = 0.3f
    }

    override fun getMotionAnimThreshold(animatable: T): Float {
        return 0.0025f
    }
}