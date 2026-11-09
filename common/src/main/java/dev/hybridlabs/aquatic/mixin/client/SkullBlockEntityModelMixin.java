package dev.hybridlabs.aquatic.mixin.client;

import com.google.common.collect.ImmutableMap;
import dev.hybridlabs.aquatic.block.PlushieBlock;
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockEntityModelMixin {
    @Inject(method = "createModel", at = @At("HEAD"), cancellable = true)
    private static void hybrid$plushieModel(EntityModelSet models, SkullBlock.Type type,
                                           CallbackInfoReturnable<SkullModelBase> cir) {
        if (type instanceof PlushieBlock.Variant) {
            ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> builder = ImmutableMap.builder();
            HAEntityModelLayers.INSTANCE.injectModels(models, builder);
            cir.setReturnValue(builder.build().get(type));
        }
    }

    @Inject(method = "getSkullRenderType", at = @At("HEAD"), cancellable = true)
    private static void hybrid$plushieTexture(SkullBlock.Type type, Identifier texture,
                                             CallbackInfoReturnable<RenderType> cir) {
        if (type instanceof PlushieBlock.Variant variant)
            cir.setReturnValue(RenderTypes.entityCutoutZOffset(texture != null ? texture : variant.getTextureLocation()));
    }
}
