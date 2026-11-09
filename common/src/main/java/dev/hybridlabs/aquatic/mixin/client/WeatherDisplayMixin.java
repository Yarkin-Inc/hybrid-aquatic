package dev.hybridlabs.aquatic.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.hybridlabs.aquatic.CommonClass;
import dev.hybridlabs.aquatic.client.weather.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherEffectRenderer.class)
public abstract class WeatherDisplayMixin implements WeatherRendererAccess {
    @Unique private MarineSnowRenderer hybrid$marineRenderer;
    @Invoker("render") public abstract void hybrid$renderWithPipeline(WeatherRenderState state, RenderPass pass, RenderPipeline pipeline);

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void hybrid$extract(ClientLevel level, float partialTick, Vec3 camera, WeatherRenderState state, CallbackInfo ci) {
        if (!((Object)this instanceof MarineSnowRenderer))
            ((MarineSnowStateAccess)state).hybrid$setMarineSnow(MarineSnowRenderer.extract(level, partialTick, camera));
    }

    @Inject(method = "prepare", at = @At("TAIL"))
    private void hybrid$prepare(Vec3 camera, WeatherRenderState state, CallbackInfo ci) {
        WeatherRenderState marine = ((MarineSnowStateAccess)state).hybrid$getMarineSnow();
        if (marine == null || marine.snowColumns.isEmpty()) return;
        if (hybrid$marineRenderer == null) hybrid$marineRenderer = new MarineSnowRenderer();
        hybrid$marineRenderer.prepare(camera, marine);
    }

    @Inject(method = "render(Lnet/minecraft/client/renderer/state/level/WeatherRenderState;Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;)V", at = @At("TAIL"))
    private void hybrid$render(WeatherRenderState state, RenderPass pass, RenderPipeline pipeline, CallbackInfo ci) {
        WeatherRenderState marine = ((MarineSnowStateAccess)state).hybrid$getMarineSnow();
        if (hybrid$marineRenderer != null && marine != null && !marine.snowColumns.isEmpty())
            ((WeatherRendererAccess)(Object)hybrid$marineRenderer).hybrid$renderWithPipeline(marine, pass, pipeline);
    }

    @ModifyExpressionValue(method = "prepare", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/WeatherEffectRenderer;SNOW_LOCATION:Lnet/minecraft/resources/Identifier;"))
    private Identifier hybrid$texture(Identifier original) {
        return (Object)this instanceof MarineSnowRenderer ? CommonClass.locate("textures/environment/marine_snow.png") : original;
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void hybrid$close(CallbackInfo ci) {
        if (hybrid$marineRenderer != null) { hybrid$marineRenderer.close(); hybrid$marineRenderer = null; }
    }
}
