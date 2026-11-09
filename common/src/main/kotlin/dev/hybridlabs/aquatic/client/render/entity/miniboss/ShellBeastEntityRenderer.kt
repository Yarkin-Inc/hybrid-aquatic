package dev.hybridlabs.aquatic.client.render.entity.miniboss

import dev.hybridlabs.aquatic.client.model.entity.miniboss.ShellBeastEntityModel
import dev.hybridlabs.aquatic.entity.miniboss.ShellBeastEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer

class ShellBeastEntityRenderer(context: Context) :
    HAMinibossEntityRenderer<ShellBeastEntity>(context, ShellBeastEntityModel()) {
    override fun getMotionAnimThreshold(animatable: ShellBeastEntity): Float {
        return 0.0025f
    }

    override fun getDeathMaxRotation(state: com.geckolib.renderer.base.GeoRenderState): Float {
        return 0f
    }

    init {
        this.withRenderLayer(AutoGlowingGeoLayer(this))
        this.shadowRadius = 1.5f
    }
}