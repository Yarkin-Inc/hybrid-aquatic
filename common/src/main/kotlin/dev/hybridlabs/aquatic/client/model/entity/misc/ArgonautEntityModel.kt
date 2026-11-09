package dev.hybridlabs.aquatic.client.model.entity.misc

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity
import net.minecraft.client.model.geom.PartNames
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

@Suppress("OVERRIDE_DEPRECATION")
class ArgonautEntityModel<T : ArgonautEntity>() :
    GeoModel<T>() {
    override fun getModelResource(animatable: T): Identifier {
        return CommonClass.locate("misc/argonaut")
    }

    override fun getTextureResource(animatable: T): Identifier {
        return if (animatable.isGlowing()) {
            CommonClass.locate("textures/entity/misc/argonaut/glowing_argonaut.png")
        } else {
            CommonClass.locate("textures/entity/misc/argonaut/argonaut.png")
        }
    }

    fun getVisorTextureResource(): Identifier {
        return CommonClass.locate("textures/entity/misc/argonaut/argonaut_visor.png")
    }

    fun getShellTextureResource(animatable: T): Identifier {
        val textureName = when (val color = animatable.getShellColor()) {
            ArgonautEntity.ShellColor.NONE -> "argonaut_shell"
            else -> color.name.lowercase() + "_argonaut_shell"
        }
        return CommonClass.locate("textures/entity/misc/argonaut/shell/$textureName.png")
    }

    fun getSailTextureResource(animatable: T): Identifier {
        val textureName = when (val color = animatable.getSailColor()) {
            ArgonautEntity.SailColor.NONE -> "argonaut_sail"
            else -> color.name.lowercase() + "_argonaut_sail"
        }
        return CommonClass.locate("textures/entity/misc/argonaut/sail/$textureName.png")
    }

    override fun getAnimationResource(animatable: T): Identifier {
        return CommonClass.locate("entity/misc/argonaut")
    }

    override fun getRenderType(animatable: T, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityCutout(texture)
    }

    override fun setCustomAnimations(
        animatable: T,
        instanceId: Long,
        animationState: PoseContext,
    ) {
        val deltaTime: Float = animationState.partialTick
        val body = animationState.bones.getBone(PartNames.BODY)

        val yawController = if (animatable.hasControllingPassenger()) animatable.controllingPassenger!! else animatable

        val yaw = Mth.rotLerp(deltaTime, yawController.yRotO, yawController.yRot)
        body.rotY = -yaw * Mth.DEG_TO_RAD

        val tilt = Mth.rotLerp(deltaTime, yawController.xRotO, yawController.xRot)
        body.rotX = tilt * -Mth.DEG_TO_RAD
    }
}