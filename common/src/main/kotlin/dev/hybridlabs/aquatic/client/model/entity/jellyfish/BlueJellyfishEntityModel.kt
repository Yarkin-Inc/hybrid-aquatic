package dev.hybridlabs.aquatic.client.model.entity.jellyfish

import dev.hybridlabs.aquatic.entity.jellyfish.BlueJellyfishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseJellyfishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class BlueJellyfishEntityModel : BaseJellyfishEntityModel<BlueJellyfishEntity>("hybrid_aquatic", "blue_jellyfish") {
    override fun getRenderType(animatable: BlueJellyfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}
