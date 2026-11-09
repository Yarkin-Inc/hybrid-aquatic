package dev.hybridlabs.aquatic.client.render.block.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.hybridlabs.aquatic.block.entity.GiantGreenAnemoneBlockEntity
import dev.hybridlabs.aquatic.client.model.block.entity.GiantGreenAnemoneBlockEntityModel
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.level.block.DirectionalBlock
import dev.hybridlabs.hapi.client.render.HybridGeoBlockRenderer as GeoBlockRenderer

class GiantGreenAnemoneBlockEntityRenderer(context: BlockEntityRendererProvider.Context) :
    GeoBlockRenderer<GiantGreenAnemoneBlockEntity>(context, GiantGreenAnemoneBlockEntityModel()) {

    override fun preRenderPass(pass: com.geckolib.renderer.base.RenderPassInfo<net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState>, collector: net.minecraft.client.renderer.SubmitNodeCollector) {
        val poseStack = pass.poseStack()
        val facing = pass.getOrDefaultGeckolibData(com.geckolib.renderer.GeoBlockRenderer.DIRECTION_FACING, Direction.NORTH)

        when(facing) {
            Direction.NORTH -> poseStack.rotateDegrees(Axis.YP, 180f)
            Direction.SOUTH -> poseStack.rotateDegrees(Axis.YP, 0f)
            Direction.WEST  -> poseStack.rotateDegrees(Axis.YP, -90f)
            Direction.EAST  -> poseStack.rotateDegrees(Axis.YP, 90f)
            else -> {}
        }

        when (facing) {
            Direction.DOWN -> {
                poseStack.rotateDegrees(Axis.XP, -90f)
                poseStack.translate(0.0, -0.5, 0.5)
            }
            Direction.UP -> {
                poseStack.rotateDegrees(Axis.XP, -90f)
                poseStack.translate(0.0, -0.5, -0.5)
            }
            Direction.NORTH -> {
                poseStack.rotateDegrees(Axis.XP, 90f)
                poseStack.translate(-1.0, -1.0, -1.0)
            }
            Direction.SOUTH -> {
                poseStack.rotateDegrees(Axis.XP, 90f)
                poseStack.translate(0.0, 0.0, -1.0)
            }
            Direction.WEST -> {
                poseStack.rotateDegrees(Axis.XP, 90f)
                poseStack.translate(0.0, -1.0, -1.0)
            }
            Direction.EAST -> {
                poseStack.rotateDegrees(Axis.XP, 90f)
                poseStack.translate(-1.0, 0.0, -1.0)
            }

            else -> throw IncompatibleClassChangeError()
        }

        super.preRenderPass(pass, collector)
    }
}
