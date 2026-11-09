package dev.hybridlabs.aquatic.client.model.entity.jellyfish

import dev.hybridlabs.aquatic.entity.jellyfish.NomuraJellyfishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseJellyfishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class NomuraJellyfishEntityModel : BaseJellyfishEntityModel<NomuraJellyfishEntity>("hybrid_aquatic", "nomura_jellyfish") {
    override fun getRenderType(animatable: NomuraJellyfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }
}
