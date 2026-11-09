package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.DanioEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class DanioEntityModel : BaseFishEntityModel<DanioEntity>("hybrid_aquatic", "danio") {
    override fun getRenderType(animatable: DanioEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}