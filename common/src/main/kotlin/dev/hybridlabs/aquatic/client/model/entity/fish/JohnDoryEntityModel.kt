package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.aquatic.entity.fish.JohnDoryEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class JohnDoryEntityModel : BaseFishEntityModel<JohnDoryEntity>("hybrid_aquatic", "john_dory") {
    override fun getRenderType(animatable: JohnDoryEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}

