package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.SeahorseEntity
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import com.geckolib.constant.DataTickets

class SeahorseEntityModel : BaseFishEntityModel<SeahorseEntity>("hybrid_aquatic", "seahorse") {

    override fun getTextureResource(animatable: SeahorseEntity): Identifier {
        return when (animatable.variant) {
            SeahorseEntity.Companion.Type.COMMON -> COMMON_TEXTURE
            SeahorseEntity.Companion.Type.PYGMY -> PYGMY_TEXTURE
            SeahorseEntity.Companion.Type.THORNY -> THORNY_TEXTURE
            SeahorseEntity.Companion.Type.BIG_BELLY -> BIG_BELLY_TEXTURE
        }
    }

    override fun setCustomAnimations(
        animatable: SeahorseEntity,
        instanceId: Long,
        animationState: PoseContext,
    ) {
        val head = animationState.bones.getBone("head")

        if (head != null) {
            val entityData = animationState.entityModelData

            head.rotX = entityData!!.headPitch() * Mth.DEG_TO_RAD
            head.rotY = entityData.netHeadYaw() * Mth.DEG_TO_RAD
        }
    }

    companion object {
        private val COMMON_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seahorse/seahorse_common.png")
        private val PYGMY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seahorse/seahorse_pygmy.png")
        private val THORNY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seahorse/seahorse_thorny.png")
        private val BIG_BELLY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seahorse/seahorse_big_belly.png")
    }
}
