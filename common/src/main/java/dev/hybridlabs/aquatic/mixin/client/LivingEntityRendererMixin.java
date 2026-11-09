package dev.hybridlabs.aquatic.mixin.client;

import com.geckolib.renderer.base.GeoRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hybridlabs.aquatic.client.render.HARenderData;
import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void hybrid$captureArgonaut(LivingEntity entity, LivingEntityRenderState state, float partialTick, CallbackInfo ci) {
        float pitch = Float.NaN;
        if (entity.getRootVehicle() instanceof ArgonautEntity argonaut) {
            pitch = argonaut.getViewXRot(partialTick);
            state.xRot = 0;
        }
        ((GeoRenderState)state).addGeckolibData(HARenderData.ARGONAUT_PITCH, pitch);
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;isBodyVisible(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Z"))
    private void hybrid$rotateInArgonaut(LivingEntityRenderState state, PoseStack poses, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        Float pitch = ((GeoRenderState)state).getGeckolibData(HARenderData.ARGONAUT_PITCH);
        if (pitch != null && !Float.isNaN(pitch)) {
            poses.scale(-1f, -1f, 1f);
            poses.rotateDegrees(Axis.XP, -pitch);
            poses.scale(-1f, -1f, 1f);
        }
    }
}
