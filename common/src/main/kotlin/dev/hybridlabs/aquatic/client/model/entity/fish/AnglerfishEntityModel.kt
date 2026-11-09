package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.AnglerfishEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class AnglerfishEntityModel : BaseFishEntityModel<AnglerfishEntity>("hybrid_aquatic","anglerfish") {
    override fun getRenderType(animatable: AnglerfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}