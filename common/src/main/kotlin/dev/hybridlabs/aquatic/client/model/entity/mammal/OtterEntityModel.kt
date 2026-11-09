package dev.hybridlabs.aquatic.client.model.entity.mammal

import dev.hybridlabs.aquatic.entity.mammal.OtterEntity
import dev.hybridlabs.aquatic.entity.mammal.OtterEntity.Companion.OtterAction
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseMammalEntityModel
import net.minecraft.client.model.geom.PartNames
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import com.geckolib.constant.DataTickets

class OtterEntityModel : BaseMammalEntityModel<OtterEntity>("hybrid_aquatic", "otter") {

    companion object {
        private val RIVER_OTTER_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/mammal/otter/river_otter.png")
        private val BABY_RIVER_OTTER_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/mammal/otter/baby_river_otter.png")
        private val SEA_OTTER_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/mammal/otter/sea_otter.png")
        private val BABY_SEA_OTTER_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/mammal/otter/baby_sea_otter.png")

        private val RIVER_OTTER_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "mammal/otter/river_otter")
        private val BABY_RIVER_OTTER_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "mammal/otter/baby_river_otter")
        private val SEA_OTTER_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "mammal/otter/sea_otter")
        private val BABY_SEA_OTTER_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "mammal/otter/baby_sea_otter")

        private val RIVER_OTTER_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/mammal/otter/river_otter")
        private val SEA_OTTER_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/mammal/otter/sea_otter")
    }

    override fun getTextureResource(animatable: OtterEntity): Identifier {
        return if (animatable.isBaby) {
            when (animatable.variant) {
                OtterEntity.Companion.Type.RIVER -> BABY_RIVER_OTTER_TEXTURE
                OtterEntity.Companion.Type.SEA -> BABY_SEA_OTTER_TEXTURE
            }
        } else {
            when (animatable.variant) {
                OtterEntity.Companion.Type.RIVER -> RIVER_OTTER_TEXTURE
                OtterEntity.Companion.Type.SEA -> SEA_OTTER_TEXTURE
            }
        }
    }


    override fun getModelResource(animatable: OtterEntity): Identifier {
        return if (animatable.isBaby) {
            when (animatable.variant) {
                OtterEntity.Companion.Type.RIVER -> BABY_RIVER_OTTER_MODEL
                OtterEntity.Companion.Type.SEA -> BABY_SEA_OTTER_MODEL
            }
        } else {
            when (animatable.variant) {
                OtterEntity.Companion.Type.RIVER -> RIVER_OTTER_MODEL
                OtterEntity.Companion.Type.SEA -> SEA_OTTER_MODEL
            }
        }
    }

    override fun getAnimationResource(animatable: OtterEntity): Identifier {
        return when (animatable.variant) {
            OtterEntity.Companion.Type.RIVER -> RIVER_OTTER_ANIMATION
            OtterEntity.Companion.Type.SEA -> SEA_OTTER_ANIMATION
        }
    }

    override fun setCustomAnimations(
        animatable: OtterEntity,
        instanceId: Long,
        animationState: PoseContext,
    ) {
        val deltaTime: Float = animationState.partialTick
        val body = animationState.bones.getBone(PartNames.BODY)

        if (!animationState.isMoving && animatable.isInWater && !animatable.onGround() && animatable.getAction() == OtterAction.FLOATING) {
            body?.multiplyRotX(0.9f)
        } else {
            val head = animationState.bones.getBone("head")

            if (head != null) {
                val entityData = animationState.entityModelData

                head.rotX = entityData!!.headPitch() * Mth.DEG_TO_RAD
                head.rotY = entityData.netHeadYaw() * Mth.DEG_TO_RAD
            }

            val xRot = Mth.clamp(Mth.lerp(deltaTime, animatable.xRotO, animatable.xRot), -45f, 45f)
            body?.rotX = xRot * -Mth.DEG_TO_RAD
        }
    }
}
