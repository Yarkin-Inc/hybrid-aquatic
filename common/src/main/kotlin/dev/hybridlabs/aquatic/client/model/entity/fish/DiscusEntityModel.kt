package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.DiscusEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class DiscusEntityModel : BaseFishEntityModel<DiscusEntity>("hybrid_aquatic", "discus") {
    override fun getRenderType(animatable: DiscusEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}
