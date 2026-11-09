package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.aquatic.entity.fish.RatfishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import kotlin.random.Random

class RatfishEntityModel : BaseFishEntityModel<RatfishEntity>("hybrid_aquatic", "ratfish") {
    override fun getRenderType(animatable: RatfishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }


    override fun getTextureResource(animatable: RatfishEntity): Identifier {
        val seed = animatable.uuid.leastSignificantBits
        val random = Random(seed)
        return commonTextures[random.nextInt(commonTextures.size)]
    }

    companion object {
        private val commonTextures = listOf(
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/ratfish/ratfish_brown.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/ratfish/ratfish_silver.png"),
        )
    }
}
