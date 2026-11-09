package dev.hybridlabs.aquatic.client.render.item;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoArmorRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/** Bridges Trinkets' submission callback to GeckoLib's per-piece render state. */
public record GeoTrinketRenderer(EquipmentSlot equipmentSlot) implements TrinketRenderer {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void submit(ItemStack stack, TrinketSlotAccess slot, EntityModel<? extends LivingEntityRenderState> model,
                       PoseStack poses, SubmitNodeCollector collector, int light, LivingEntityRenderState base,
                       float headYaw, float headPitch) {
        if (!(stack.getItem() instanceof GeoItem) || !(model instanceof HumanoidModel<?> humanoid)) return;
        GeoArmorRenderer renderer = GeoRenderProvider.of(stack.getItem()).getGeoArmorRenderer(stack, equipmentSlot);
        if (renderer == null) return;
        var entity = slot.inventory().getAttachment().getEntity();
        var state = new HumanoidRenderState();
        state.entityType = base.entityType;
        state.x = base.x; state.y = base.y; state.z = base.z;
        state.ageInTicks = base.ageInTicks;
        state.isBaby = base.isBaby;
        state.isInvisible = base.isInvisible;
        state.isInvisibleToPlayer = base.isInvisibleToPlayer;
        state.hasRedOverlay = base.hasRedOverlay;
        state.lightCoords = light;
        state.outlineColor = base.outlineColor;
        state.scale = base.scale;
        state.ageScale = base.ageScale;
        state.pose = base.pose;
        state.yRot = base.yRot; state.xRot = base.xRot; state.bodyRot = base.bodyRot;
        float partialTick = base.ageInTicks - entity.tickCount;
        renderer.fillRenderState((GeoItem)stack.getItem(),
            new GeoArmorRenderer.RenderData(stack, equipmentSlot, entity, humanoid), state, partialTick);
        renderer.performRenderPass(state, poses, collector,
            Minecraft.getInstance().gameRenderer.gameRenderState().levelRenderState.cameraRenderState);
    }
}
