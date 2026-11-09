package dev.hybridlabs.aquatic.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hybridlabs.aquatic.block.PlushieBlock;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public abstract class HeadFeatureRendererMixin {
    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void hybrid$translatePlushie(PoseStack poses, SubmitNodeCollector collector, int light,
                                       LivingEntityRenderState state, float yaw, float pitch, CallbackInfo ci) {
        if (state.wornHeadType instanceof PlushieBlock.Variant) poses.translate(0, -6.75 / 16.0, 0);
    }
}
