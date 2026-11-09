package dev.hybridlabs.aquatic.mixin.client;

import dev.hybridlabs.aquatic.effect.HAMobEffects;
import dev.hybridlabs.aquatic.fog.ClarityFogModifier;
import dev.hybridlabs.aquatic.fog.ThalassophobiaFogModifier;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "setupFog", at = @At("RETURN"))
    private void hybrid$renderFog(Camera camera, int chunks, DeltaTracker delta, float darken, ClientLevel level,
                                 CallbackInfoReturnable<FogData> cir) {
        if (camera.entity() instanceof LocalPlayer player && camera.getFluidInCamera() == FogType.WATER) {
            var clarity = player.getEffect(HAMobEffects.INSTANCE.getCLARITY().asHolder());
            var fear = player.getEffect(HAMobEffects.INSTANCE.getTHALASSOPHOBIA().asHolder());
            if (clarity != null) new ClarityFogModifier().setupFog(cir.getReturnValue(), player, clarity, chunks * 16f, delta.getGameTimeDeltaPartialTick(false));
            else if (fear != null) new ThalassophobiaFogModifier().setupFog(cir.getReturnValue(), player, fear, chunks * 16f, delta.getGameTimeDeltaPartialTick(false));
        }
    }
}
