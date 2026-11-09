package dev.hybridlabs.aquatic.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hybridlabs.aquatic.CommonClass;
import dev.hybridlabs.aquatic.access.CustomFishingBobberEntityData;
import dev.hybridlabs.aquatic.item.HAItems;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHookRenderer.class)
public abstract class FishingBobberEntityRendererMixin {
    @Unique
    private static final RenderType BARBED_HOOK_LAYER = getRenderType(CommonClass.locate("textures/entity/bobber" +
            "/barbed_bobber.png"));
    @Unique
    private static final RenderType GLOWING_HOOK_LAYER = getRenderType(CommonClass.locate("textures/entity/bobber" +
            "/glowing_bobber.png"));
    @Unique
    private static final RenderType MAGNETIC_HOOK_LAYER =
            getRenderType(CommonClass.locate("textures/entity/bobber" + "/magnetic_bobber.png"));
    @Unique
    private static final RenderType CREEPERMAGNET_HOOK_LAYER = getRenderType(CommonClass.locate("textures/entity" +
            "/bobber/creepermagnet_bobber.png"));
    @Unique
    private static final RenderType OMINOUS_HOOK_LAYER = getRenderType(CommonClass.locate("textures/entity/bobber" +
            "/ominous_bobber.png"));

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;F)V", at = @At("TAIL"))
    private void hybrid$captureLure(FishingHook entity, net.minecraft.client.renderer.entity.state.FishingHookRenderState state, float partialTick, CallbackInfo ci) {
        RenderType currentRenderType = null;
        ItemStack currentStack = ((CustomFishingBobberEntityData)entity).getLureItem();
        if (currentStack.is(HAItems.INSTANCE.getBARBED_HOOK().get())) currentRenderType = BARBED_HOOK_LAYER;
        else if (currentStack.is(HAItems.INSTANCE.getGLOWING_HOOK().get())) currentRenderType = GLOWING_HOOK_LAYER;
        else if (currentStack.is(HAItems.INSTANCE.getMAGNETIC_HOOK().get())) currentRenderType = MAGNETIC_HOOK_LAYER;
        else if (currentStack.is(HAItems.INSTANCE.getCREEPERMAGNET_HOOK().get())) currentRenderType = CREEPERMAGNET_HOOK_LAYER;
        else if (currentStack.is(HAItems.INSTANCE.getOMINOUS_HOOK().get())) currentRenderType = OMINOUS_HOOK_LAYER;
        ((com.geckolib.renderer.base.GeoRenderState)state).addGeckolibData(dev.hybridlabs.aquatic.client.render.HARenderData.BOBBER_LAYER, currentRenderType);
    }

    @com.llamalad7.mixinextras.injector.ModifyExpressionValue(method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;RENDER_TYPE:Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType hybrid$lureLayer(RenderType original, @com.llamalad7.mixinextras.sugar.Local(argsOnly = true) net.minecraft.client.renderer.entity.state.FishingHookRenderState state) {
        RenderType custom = ((com.geckolib.renderer.base.GeoRenderState)state).getGeckolibData(dev.hybridlabs.aquatic.client.render.HARenderData.BOBBER_LAYER);
        return custom == null ? original : custom;
    }

    @Unique
    private static RenderType getRenderType(Identifier textureLocation) {
        return RenderTypes.entityCutout(textureLocation);
    }
}
