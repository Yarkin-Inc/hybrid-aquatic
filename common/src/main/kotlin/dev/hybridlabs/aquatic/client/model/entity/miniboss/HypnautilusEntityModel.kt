package dev.hybridlabs.aquatic.client.model.entity.miniboss

import dev.hybridlabs.aquatic.entity.miniboss.HypnautilusEntity
import net.minecraft.client.model.geom.PartNames
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext

class HypnautilusEntityModel : HAMinionEntityModel<HypnautilusEntity>("hypnautilus") {

    override fun setCustomAnimations(
        animatable: HypnautilusEntity,
        instanceId: Long,
        animationState: PoseContext
    ) {
        super.setCustomAnimations(animatable, instanceId, animationState)
        val deltaTime: Float = animationState.partialTick

        val body = animationState.bones.getBone(PartNames.BODY)

        val xRot = Mth.clamp(Mth.lerp(deltaTime, animatable.xRotO, animatable.xRot), -45f, 45f)
        body.rotX = xRot * -Mth.DEG_TO_RAD
    }
}