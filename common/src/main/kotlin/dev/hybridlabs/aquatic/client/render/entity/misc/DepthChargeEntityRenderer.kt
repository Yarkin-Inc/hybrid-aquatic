package dev.hybridlabs.aquatic.client.render.entity.misc

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.hybridlabs.aquatic.block.HABlocks
import dev.hybridlabs.aquatic.entity.misc.PrimedDepthChargeEntity
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.TntRenderer
import net.minecraft.client.renderer.entity.TntMinecartRenderer
import net.minecraft.client.renderer.entity.state.TntRenderState
import net.minecraft.client.renderer.state.level.CameraRenderState

class DepthChargeEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<PrimedDepthChargeEntity, TntRenderState>(context) {
    private val blocks = context.blockModelResolver
    init { shadowRadius = 0.5f }
    override fun createRenderState() = TntRenderState()
    override fun extractRenderState(entity: PrimedDepthChargeEntity, state: TntRenderState, partialTick: Float) {
        super.extractRenderState(entity, state, partialTick)
        state.fuseRemainingInTicks = entity.fuse - partialTick + 1f
        blocks.update(state.blockState, HABlocks.DEPTH_CHARGE.get().defaultBlockState(), TntRenderer.BLOCK_DISPLAY_CONTEXT)
    }
    override fun submit(state: TntRenderState, pose: PoseStack, collector: SubmitNodeCollector, camera: CameraRenderState) {
        pose.pushPose()
        pose.translate(0f, 0.5f, 0f)
        val swell = 1f + TntRenderer.getSwellAmount(state.fuseRemainingInTicks)
        pose.scale(swell, swell, swell)
        pose.rotateDegrees(Axis.YP, -90f)
        pose.translate(-0.5f, -0.5f, 0.5f)
        pose.rotateDegrees(Axis.YP, 90f)
        pose.scale(1.0f, 1.0f, 1.0f)
        TntMinecartRenderer.submitWhiteSolidBlock(state.blockState, pose, collector, state.lightCoords, TntRenderer.isLit(state.fuseRemainingInTicks), state.outlineColor)
        pose.popPose()
        super.submit(state, pose, collector, camera)
    }
}
