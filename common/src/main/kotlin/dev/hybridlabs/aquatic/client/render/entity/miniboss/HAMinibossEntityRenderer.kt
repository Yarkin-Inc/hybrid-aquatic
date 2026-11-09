package dev.hybridlabs.aquatic.client.render.entity.miniboss

import dev.hybridlabs.aquatic.entity.base.HAMinibossEntity
import dev.hybridlabs.hapi.client.render.HybridGeoEntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import com.geckolib.model.GeoModel

open class HAMinibossEntityRenderer<T : HAMinibossEntity>(
    context: EntityRendererProvider.Context,
    model: GeoModel<T>,
    variableSize: Boolean = false,
    canGlow: Boolean = false
) : HybridGeoEntityRenderer<T>(context, model, variableSize, canGlow, 0f, 0.0025f, 0.5f, false)
