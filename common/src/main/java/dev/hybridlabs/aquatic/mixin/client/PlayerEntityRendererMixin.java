package dev.hybridlabs.aquatic.mixin.client;

import dev.hybridlabs.aquatic.entity.misc.ArgonautEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void hybrid$setModelPose(net.minecraft.world.entity.Avatar player, AvatarRenderState state, float partialTick, CallbackInfo ci) {
        if (player.getRootVehicle() instanceof ArgonautEntity) state.isCrouching = false;
    }
}
