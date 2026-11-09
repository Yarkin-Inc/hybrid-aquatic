package dev.hybridlabs.aquatic.mixin.client;

import com.geckolib.renderer.base.GeoRenderState;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.hybridlabs.aquatic.CommonClass;
import dev.hybridlabs.aquatic.client.render.HARenderData;
import dev.hybridlabs.aquatic.tag.HAPaintingTags;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.state.PaintingRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.decoration.painting.Painting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PaintingRenderer.class)
public abstract class PaintingRendererMixin {
    @Shadow @Final private TextureAtlas paintingsAtlas;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/painting/Painting;Lnet/minecraft/client/renderer/entity/state/PaintingRenderState;F)V", at = @At("TAIL"))
    private void hybrid$captureTransparency(Painting entity, PaintingRenderState state, float partialTicks, CallbackInfo ci) {
        ((GeoRenderState)state).addGeckolibData(HARenderData.TRANSPARENT_PAINTING,
            entity.getVariant().is(HAPaintingTags.INSTANCE.getTRANSPARENT_PAINTING()));
    }

    @ModifyExpressionValue(method = "submit(Lnet/minecraft/client/renderer/entity/state/PaintingRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entitySolidZOffsetForward(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType hybrid$transparentLayer(RenderType original, @Local(argsOnly = true) PaintingRenderState state) {
        return Boolean.TRUE.equals(((GeoRenderState)state).getGeckolibData(HARenderData.TRANSPARENT_PAINTING))
            ? RenderTypes.entityTranslucent(paintingsAtlas.location()) : original;
    }

    @ModifyExpressionValue(method = "submit(Lnet/minecraft/client/renderer/entity/state/PaintingRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlas;getSprite(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
    private TextureAtlasSprite hybrid$transparentBack(TextureAtlasSprite original, @Local(argsOnly = true) PaintingRenderState state) {
        return Boolean.TRUE.equals(((GeoRenderState)state).getGeckolibData(HARenderData.TRANSPARENT_PAINTING))
            ? paintingsAtlas.getSprite(CommonClass.locate("blank")) : original;
    }
}
