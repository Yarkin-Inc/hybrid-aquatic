package dev.hybridlabs.aquatic.client.model.entity.misc

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.entity.projectile.CavitationBubbleEntity
import net.minecraft.client.model.geom.PartNames
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

class CavitationBubbleEntityModel() :
    GeoModel<CavitationBubbleEntity>() {
    override fun getModelResource(animatable: CavitationBubbleEntity): Identifier {
        return CommonClass.locate("misc/cavitation_bubble")
    }

    override fun getTextureResource(animatable: CavitationBubbleEntity): Identifier {
        return CommonClass.locate("textures/entity/misc/cavitation_bubble/cavitation_bubble.png")
    }

    override fun getAnimationResource(p0: CavitationBubbleEntity): Identifier {
        return CommonClass.locate("entity/misc/cavitation_bubble")
    }

    override fun getRenderType(animatable: CavitationBubbleEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }

    override fun setCustomAnimations(
        animatable: CavitationBubbleEntity,
        instanceId: Long,
        animationState: PoseContext
    ) {
        super.setCustomAnimations(animatable, instanceId, animationState)

        val deltaTime = animationState.partialTick
        val body = animationState.bones.getBone(PartNames.BODY)

        val tilt = Mth.clamp(
            Mth.lerp(deltaTime, animatable.xRotO, animatable.xRot),
            -45f, 45f
        )

        body?.rotX = tilt * -Mth.DEG_TO_RAD
    }
}