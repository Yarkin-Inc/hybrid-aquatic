package dev.hybridlabs.aquatic.client.render.block.entity

import com.geckolib.constant.dataticket.DataTicket
import com.geckolib.renderer.base.GeoRenderState
import com.geckolib.renderer.base.RenderPassInfo
import com.mojang.math.Axis
import dev.hybridlabs.aquatic.block.MessageInABottleBlock
import dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity
import dev.hybridlabs.aquatic.client.model.block.entity.MessageInABottleBlockEntityModel
import dev.hybridlabs.hapi.client.render.HybridGeoBlockRenderer
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED

class MessageInABottleBlockEntityRenderer(context: BlockEntityRendererProvider.Context) :
    HybridGeoBlockRenderer<MessageInABottleBlockEntity>(context, MessageInABottleBlockEntityModel()) {
    override fun addRenderData(entity: MessageInABottleBlockEntity, relatedObject: Void?, state: BlockEntityRenderState, partialTick: Float) {
        val random = RandomSource.create(Mth.getSeed(entity.blockPos.x, 0, entity.blockPos.z))
        (state as GeoRenderState).addGeckolibData(POSE, BottlePose(
            random.nextFloat() * 360f + ROTATION_PER * (entity.variant.ordinal + 1),
            if (entity.blockState.getValue(WATERLOGGED)) 0.8 else 0.001))
    }

    override fun preRenderPass(pass: RenderPassInfo<BlockEntityRenderState>, collector: SubmitNodeCollector) {
        val pose = pass.getGeckolibData(POSE) ?: BottlePose(0f, 0.001)
        val matrices = pass.poseStack()
        matrices.translate(0.5, pose.height, 0.5)
        matrices.rotateDegrees(Axis.YP, pose.rotation)
        matrices.translate(-0.5, 0.0, -0.5)
        super.preRenderPass(pass, collector)
    }

    private data class BottlePose(val rotation: Float, val height: Double)
    companion object {
        private val POSE = DataTicket.create("hybrid_aquatic:bottle_pose", BottlePose::class.java)
        private val ROTATION_PER = 360f / MessageInABottleBlock.Variant.entries.size
    }
}
