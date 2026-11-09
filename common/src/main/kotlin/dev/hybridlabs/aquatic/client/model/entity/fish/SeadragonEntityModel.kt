package dev.hybridlabs.aquatic.client.model.entity.fish

import dev.hybridlabs.hapi.client.model.entity.aquatic.BaseFishEntityModel
import dev.hybridlabs.aquatic.entity.fish.SeadragonEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth
import dev.hybridlabs.hapi.client.model.PoseContext
import com.geckolib.constant.DataTickets

class SeadragonEntityModel : BaseFishEntityModel<SeadragonEntity>("hybrid_aquatic", "seadragon") {
    override fun getRenderType(animatable: SeadragonEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }

    override fun getTextureResource(animatable: SeadragonEntity): Identifier {
        return when (animatable.variant) {
            SeadragonEntity.Companion.Type.LEAFY -> LEAFY_TEXTURE
            SeadragonEntity.Companion.Type.WEEDY -> WEEDY_TEXTURE
            SeadragonEntity.Companion.Type.RUBY -> RUBY_TEXTURE
        }
    }

    override fun getModelResource(animatable: SeadragonEntity): Identifier {
        return when (animatable.variant) {
            SeadragonEntity.Companion.Type.LEAFY -> LEAFY_MODEL
            SeadragonEntity.Companion.Type.WEEDY -> WEEDY_MODEL
            SeadragonEntity.Companion.Type.RUBY -> RUBY_MODEL
        }
    }

    override fun getAnimationResource(animatable: SeadragonEntity): Identifier {
        return when (animatable.variant) {
            SeadragonEntity.Companion.Type.LEAFY -> LEAFY_ANIMATION
            SeadragonEntity.Companion.Type.WEEDY -> WEEDY_ANIMATION
            SeadragonEntity.Companion.Type.RUBY -> RUBY_ANIMATION
        }
    }

    override fun setCustomAnimations(
        animatable: SeadragonEntity,
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
        private val LEAFY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seadragon/leafy_seadragon.png")
        private val WEEDY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seadragon/weedy_seadragon.png")
        private val RUBY_TEXTURE =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "textures/entity/fish/seadragon/ruby_seadragon.png")

        private val LEAFY_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/seadragon/leafy_seadragon")
        private val WEEDY_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/seadragon/weedy_seadragon")
        private val RUBY_MODEL =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "fish/seadragon/ruby_seadragon")

        private val LEAFY_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/seadragon/seadragon_leafy")
        private val WEEDY_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/seadragon/seadragon_weedy")
        private val RUBY_ANIMATION =
            Identifier.fromNamespaceAndPath("hybrid_aquatic", "entity/fish/seadragon/seadragon_ruby")
    }
}
