package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.aquatic.entity.fish.SnailfishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class SnailfishEntityModel : BaseFishEntityModel<SnailfishEntity>("hybrid_aquatic", "snailfish") {
    override fun getRenderType(animatable: SnailfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}
