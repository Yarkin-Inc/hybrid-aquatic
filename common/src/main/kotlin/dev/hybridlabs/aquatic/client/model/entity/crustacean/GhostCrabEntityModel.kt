package dev.hybridlabs.aquatic.client.model.entity.crustacean

import dev.hybridlabs.aquatic.entity.crustacean.GhostCrabEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseCrustaceanEntityModel
import net.minecraft.resources.Identifier
import kotlin.random.Random

class GhostCrabEntityModel : BaseCrustaceanEntityModel<GhostCrabEntity>("hybrid_aquatic", "ghost_crab") {

    companion object {
        private val commonTextures = listOf(
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/ghost_crab/ghost_crab_white.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/ghost_crab/ghost_crab_yellow.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/ghost_crab/ghost_crab_purple.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/ghost_crab/ghost_crab_red.png")
        )
    }

    override fun getTextureResource(animatable: GhostCrabEntity): Identifier {
        val seed = animatable.uuid.leastSignificantBits
        val random = Random(seed)
        return commonTextures[random.nextInt(commonTextures.size)]
    }
}
