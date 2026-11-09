package dev.hybridlabs.aquatic.client.model.entity.jellyfish

import dev.hybridlabs.aquatic.entity.jellyfish.CombJellyEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseJellyfishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

class CombJellyEntityModel : BaseJellyfishEntityModel<CombJellyEntity>("hybrid_aquatic", "comb_jelly") {
    override fun getRenderType(animatable: CombJellyEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }

    companion object {
        private val SEA_WALNUT_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/jellyfish/comb_jelly/comb_jelly.png")
        private val BLOODYBELLY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/jellyfish/comb_jelly/bloodybelly_comb_jelly.png")
    }

    override fun getTextureResource(animatable: CombJellyEntity): Identifier {
        return when (animatable.variant) {
            CombJellyEntity.Companion.Type.SEA_WALNUT -> SEA_WALNUT_TEXTURE
            CombJellyEntity.Companion.Type.BLOODYBELLY -> BLOODYBELLY_TEXTURE
        }
    }
}
