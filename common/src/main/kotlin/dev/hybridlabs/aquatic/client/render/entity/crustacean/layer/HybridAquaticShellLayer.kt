package dev.hybridlabs.aquatic.client.render.entity.crustacean.layer

import com.geckolib.cache.model.GeoBone
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.hybridlabs.aquatic.item.HAItems
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseCrustaceanEntityRenderer
import dev.hybridlabs.hapi.entity.base.aquatic.BaseCrustaceanEntity
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.client.renderer.item.ItemStackRenderState
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemDisplayContext

class HybridAquaticShellLayer<T : BaseCrustaceanEntity>(
    context: EntityRendererProvider.Context,
    renderer: BaseCrustaceanEntityRenderer<T>,
    private val defaultItem: Item
) : BlockAndItemGeoLayer<T, Void, LivingEntityRenderState>(context, renderer) {
    override fun getRelevantBones(entity: T, related: Void?, state: LivingEntityRenderState, partialTick: Float): List<RenderData> {
        if (entity.shellItem.isEmpty || entity.shellItem.`is`(defaultItem) || entity.shellItem.`is`(HAItems.OMINOUS_CONCH.get())) return emptyList()
        val item = ItemStackRenderState()
        itemModelResolver.updateForLiving(item, entity.shellItem, ItemDisplayContext.FIXED, entity)
        return listOf(RenderData.item("shell", ItemDisplayContext.FIXED, item))
    }
    override fun addRenderData(entity: T, related: Void?, state: LivingEntityRenderState, partialTick: Float) {
        state.addGeckolibData(CONTENTS, getRelevantBones(entity, related, state, partialTick))
    }
    override fun submitItemStackRender(pose: PoseStack, bone: GeoBone, item: ItemStackRenderState, display: ItemDisplayContext, state: LivingEntityRenderState, collector: SubmitNodeCollector, light: Int) {
        pose.pushPose()
        pose.rotateDegrees(Axis.XP, 32.5f)
        pose.translate(0.0, 0.25, -0.1)
        super.submitItemStackRender(pose, bone, item, display, state, collector, light)
        pose.popPose()
    }
}
