package dev.hybridlabs.aquatic.client.model.entity.crustacean

import dev.hybridlabs.aquatic.entity.crustacean.DecoratorCrabEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseCrustaceanEntityModel
import net.minecraft.resources.Identifier
import kotlin.random.Random

class DecoratorCrabEntityModel : BaseCrustaceanEntityModel<DecoratorCrabEntity>("hybrid_aquatic", "decorator_crab") {

    companion object {
        private val coralTextures = listOf(
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_brain.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_bubble.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_button.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_rose.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_leaf.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_bamboo.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_zigzag.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_fire.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_horn.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_lophelia.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_sun.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_thorn.png"),
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/crustacean/decorator_crab/decorator_crab_tube.png")
        )

        private val DECORATED_MODEL = Identifier.fromNamespaceAndPath("hybrid_aquatic", "crustacean/decorator_crab/decorator_crab")
        private val UNDECORATED_MODEL = Identifier.fromNamespaceAndPath("hybrid_aquatic", "crustacean/decorator_crab/undecorated_decorator_crab")

    }

    override fun getTextureResource(animatable: DecoratorCrabEntity): Identifier {
        val seed = animatable.uuid.leastSignificantBits
        val random = Random(seed)
        return when (animatable.variant) {
            DecoratorCrabEntity.Companion.Type.CORAL -> coralTextures[random.nextInt(coralTextures.size)]
        }
    }

    override fun getModelResource(animatable: DecoratorCrabEntity): Identifier {
        return if (animatable.coralTimer > 0) {
            UNDECORATED_MODEL
        } else {
            DECORATED_MODEL
        }
    }
}
