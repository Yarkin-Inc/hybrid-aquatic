package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.AfricanButterflyfishEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class AfricanButterflyfishEntityModel : BaseFishEntityModel<AfricanButterflyfishEntity>("hybrid_aquatic","african_butterflyfish") {
    override fun getRenderType(animatable: AfricanButterflyfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}