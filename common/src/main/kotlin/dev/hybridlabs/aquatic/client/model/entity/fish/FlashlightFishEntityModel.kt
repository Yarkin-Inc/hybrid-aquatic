package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.aquatic.entity.fish.FlashlightFishEntity
import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import net.minecraft.client.model.geom.PartNames
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import kotlin.math.abs

class FlashlightFishEntityModel : BaseFishEntityModel<FlashlightFishEntity>("hybrid_aquatic", "flashlight_fish") {
    override fun getRenderType(animatable: FlashlightFishEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }

    companion object {
        private val ONE_FISH_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/flashlight_fish/flashlight_fish")
        private val TWO_FISH_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/flashlight_fish/flashlight_fish_two")
        private val THREE_FISH_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/flashlight_fish/flashlight_fish_three")

        private val ONE_FISH_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/flashlight_fish/flashlight_fish")
        private val TWO_FISH_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/flashlight_fish/flashlight_fish_two")
        private val THREE_FISH_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/flashlight_fish/flashlight_fish_three")
    }

    override fun getModelResource(animatable: FlashlightFishEntity): Identifier {
        val fish = animatable.getFishCount()

        return when (fish) {
            1 -> ONE_FISH_MODEL
            2 -> TWO_FISH_MODEL
            3 -> THREE_FISH_MODEL
            else -> ONE_FISH_MODEL
        }
    }

    override fun getAnimationResource(animatable: FlashlightFishEntity): Identifier {
        val fish = animatable.getFishCount()

        return when (fish) {
            1 -> ONE_FISH_ANIMATION
            2 -> TWO_FISH_ANIMATION
            3 -> THREE_FISH_ANIMATION
            else -> ONE_FISH_ANIMATION
        }
    }

    override fun setCustomAnimations(
        animatable: FlashlightFishEntity,
        instanceId: Long,
        animationState: PoseContext
    ) {
        super.setCustomAnimations(animatable, instanceId, animationState)
        val deltaTime: Float = animationState.partialTick

        val body = animationState.bones.getBone(PartNames.BODY)
        val body2 = animationState.bones.getBone("body_2")
        val body3 = animationState.bones.getBone("body_3")
        val tail2 = animationState.bones.getBone("tail2")
        val tail3 = animationState.bones.getBone("tail3")
        val tailFin2 = animationState.bones.getBone("tail_fin2")
        val tailFin3 = animationState.bones.getBone("tail_fin3")

        val xRot = Mth.clamp(Mth.lerp(deltaTime, animatable.xRot, animatable.xRotO), -45f, 45f)
        body.rotX = xRot * -Mth.DEG_TO_RAD
        body2?.rotX = xRot * -Mth.DEG_TO_RAD
        body3?.rotX = xRot * -Mth.DEG_TO_RAD

        val yawDiff = animatable.yRot - animatable.yRotO
        val targetRoll = Mth.clamp(yawDiff * 3f, -30f, 30f)

        val turnSpeed = abs(yawDiff)
        val smoothing = Mth.clamp(0.05f + turnSpeed * 0.02f, 0.05f, 0.25f)
        animatable.currentRoll = Mth.lerp(smoothing, animatable.currentRoll, targetRoll)

        val roll = Mth.lerp(deltaTime, animatable.prevRoll, animatable.currentRoll)
        body.rotZ = roll * -Mth.DEG_TO_RAD
        body2?.rotZ = roll * -Mth.DEG_TO_RAD
        body3?.rotZ = roll * -Mth.DEG_TO_RAD
        tail2?.rotY = roll * Mth.DEG_TO_RAD
        tail3?.rotY = roll * Mth.DEG_TO_RAD
        tailFin2?.rotY = roll * Mth.DEG_TO_RAD
        tailFin3?.rotY = roll * Mth.DEG_TO_RAD
    }
}
