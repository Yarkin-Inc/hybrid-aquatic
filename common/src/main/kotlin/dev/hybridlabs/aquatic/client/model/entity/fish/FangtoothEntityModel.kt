package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.aquatic.entity.fish.FangtoothEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class FangtoothEntityModel : BaseFishEntityModel<FangtoothEntity>("hybrid_aquatic", "fangtooth") {
    override fun getRenderType(animatable: FangtoothEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}