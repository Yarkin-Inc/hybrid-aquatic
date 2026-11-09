package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.GardenEelEntity
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import com.geckolib.constant.DataTickets

class GardenEelEntityModel : BaseFishEntityModel<GardenEelEntity>("hybrid_aquatic", "garden_eel") {

    override fun setCustomAnimations(
        animatable: GardenEelEntity,
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
}
