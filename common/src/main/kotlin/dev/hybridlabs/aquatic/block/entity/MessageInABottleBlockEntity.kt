package dev.hybridlabs.aquatic.block.entity

import dev.hybridlabs.aquatic.block.MessageInABottleBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED
import com.geckolib.animatable.GeoAnimatable
import com.geckolib.animatable.instance.AnimatableInstanceCache
import com.geckolib.animation.*
import com.geckolib.animation.state.AnimationTest
import com.geckolib.animatable.manager.AnimatableManager
import com.geckolib.animation.`object`.PlayState
import com.geckolib.util.GeckoLibUtil
import com.geckolib.util.RenderUtil

/**
 * Represents the block entity for Message in a Bottle blocks.
 * @see dev.hybridlabs.aquatic.block.MessageInABottleBlock
 */
class MessageInABottleBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(HABlockEntityTypes.MESSAGE_IN_A_BOTTLE.get(), pos, state),
    GeoAnimatable {
    private val instanceCache = GeckoLibUtil.createInstanceCache(this)

    /**
     * The variant of this bottle.
     */
    var variant: MessageInABottleBlock.Variant = MessageInABottleBlock.Variant.BOTTLE

    override fun saveAdditional(tag: net.minecraft.world.level.storage.ValueOutput) {
        super.saveAdditional(tag)
        tag.putString(VARIANT_KEY, variant.id)
    }

    override fun loadAdditional(tag: net.minecraft.world.level.storage.ValueInput) {
        super.loadAdditional(tag)
        variant = MessageInABottleBlock.Variant.byId(tag.getStringOr(VARIANT_KEY, "bottle"))
    }

    override fun collectImplicitComponents(builder: net.minecraft.core.component.DataComponentMap.Builder) {
        super.collectImplicitComponents(builder)
        val data = CompoundTag().apply { putString(VARIANT_KEY, variant.id) }
        builder.set(net.minecraft.core.component.DataComponents.BLOCK_ENTITY_DATA,
            net.minecraft.world.item.component.TypedEntityData.of(type, data))
    }

    private fun <E> predicate(
        event: AnimationTest<E>
    ): PlayState where E : BlockEntity, E : GeoAnimatable {
        return if (blockState.hasProperty(WATERLOGGED) && blockState.getValue(WATERLOGGED)) {
            event.controller.setAnimation(WATER_BOB_ANIMATION)
            PlayState.CONTINUE
        } else {
            PlayState.STOP
        }
    }

    override fun registerControllers(controllerRegistrar: AnimatableManager.ControllerRegistrar) {
        controllerRegistrar.add(AnimationController<MessageInABottleBlockEntity>( "controller", 0, ::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return instanceCache
    }


    override fun getUpdateTag(registries: HolderLookup.Provider): CompoundTag {
        return saveWithoutMetadata(registries)
    }

    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    companion object {
        /**
         * The nbt key for the variant id.
         */
        const val VARIANT_KEY = "variant"

        val WATER_BOB_ANIMATION: RawAnimation = RawAnimation.begin().then("water_bob", com.geckolib.animation.`object`.LoopType.LOOP)
    }
}
