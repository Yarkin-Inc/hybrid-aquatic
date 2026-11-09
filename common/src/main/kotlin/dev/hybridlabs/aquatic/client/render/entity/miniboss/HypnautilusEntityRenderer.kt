package dev.hybridlabs.aquatic.client.render.entity.miniboss

import dev.hybridlabs.aquatic.client.model.entity.miniboss.HypnautilusEntityModel
import dev.hybridlabs.aquatic.entity.miniboss.HypnautilusEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer

class HypnautilusEntityRenderer(context: Context) :
    HAMinionEntityRenderer<HypnautilusEntity>(context, HypnautilusEntityModel()) {
    override fun getMotionAnimThreshold(animatable: HypnautilusEntity): Float {
        return 0.0025f
    }

    override fun getDeathMaxRotation(state: com.geckolib.renderer.base.GeoRenderState): Float {
        return 180f
    }

    override fun addRenderData(entity: HypnautilusEntity, relatedObject: Void?, state: net.minecraft.client.renderer.entity.state.LivingEntityRenderState, partialTick: Float) {
        super.addRenderData(entity, relatedObject, state, partialTick)
        entity.registerHypnotizing()
    }

    init {
        this.withRenderLayer(AutoGlowingGeoLayer(this))
        this.shadowRadius = 0.4f
    }
}