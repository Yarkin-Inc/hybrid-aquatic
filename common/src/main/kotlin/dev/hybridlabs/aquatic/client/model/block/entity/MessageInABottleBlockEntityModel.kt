package dev.hybridlabs.aquatic.client.model.block.entity

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.block.MessageInABottleBlock.Variant
import dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED
import dev.hybridlabs.hapi.client.model.PoseContext
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

class MessageInABottleBlockEntityModel : GeoModel<MessageInABottleBlockEntity>() {
    @Deprecated("Deprecated in Java")
    override fun getModelResource(blockEntity: MessageInABottleBlockEntity): Identifier {
        return VARIANT_MODELS[blockEntity.variant] ?: throw NotImplementedError("Model not registered")
    }

    @Deprecated("Deprecated in Java")
    override fun getTextureResource(blockEntity: MessageInABottleBlockEntity): Identifier {
        return VARIANT_TEXTURES[blockEntity.variant] ?: throw NotImplementedError("Model not registered")
    }

    override fun getAnimationResource(blockEntity: MessageInABottleBlockEntity): Identifier {
        return WATER_BOB_ANIMATION_ID
    }

    override fun getRenderType(animatable: MessageInABottleBlockEntity, texture: Identifier): RenderType {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture)
    }

    override fun setCustomAnimations(
        animatable: MessageInABottleBlockEntity,
        instanceId: Long,
        animationState: PoseContext
    ) {
        if (animatable.blockState.getValue(WATERLOGGED))
            super.setCustomAnimations(animatable, instanceId, animationState)
    }

    companion object {
        val VARIANT_MODELS = mapOf(
            Variant.BOTTLE to CommonClass.locate("entity/block/message_in_a_bottle/message_in_a_bottle"),
            Variant.JAR to CommonClass.locate("entity/block/message_in_a_bottle/message_in_a_jar"),
            Variant.LONGNECK to CommonClass.locate("entity/block/message_in_a_bottle/message_in_a_longneck_bottle"),
            Variant.POTION to CommonClass.locate("entity/block/message_in_a_bottle/message_in_a_potion_bottle"),
            Variant.WINE to CommonClass.locate("entity/block/message_in_a_bottle/message_in_a_wine_bottle"),
        )

        val VARIANT_TEXTURES = mapOf(
            Variant.BOTTLE to CommonClass.locate("textures/entity/block/message_in_a_bottle/message_in_a_bottle.png"),
            Variant.JAR to CommonClass.locate("textures/entity/block/message_in_a_bottle/message_in_a_jar.png"),
            Variant.LONGNECK to CommonClass.locate("textures/entity/block/message_in_a_bottle/message_in_a_longneck_bottle.png"),
            Variant.POTION to CommonClass.locate("textures/entity/block/message_in_a_bottle/message_in_a_potion_bottle.png"),
            Variant.WINE to CommonClass.locate("textures/entity/block/message_in_a_bottle/message_in_a_wine_bottle.png"),
        )

        val WATER_BOB_ANIMATION_ID: Identifier = CommonClass.locate("water_bob")
    }
}