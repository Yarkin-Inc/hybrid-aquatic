package dev.hybridlabs.aquatic.mixin;

import dev.hybridlabs.aquatic.tag.HAPaintingTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Painting.class)
public abstract class PaintingMixin {
    @Shadow public abstract Holder<PaintingVariant> getVariant();

    @ModifyArg(method = "dropItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/painting/Painting;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/item/ItemEntity;"), index = 1)
    private ItemStack hybrid$preserveVariant(ItemStack stack) {
        if (getVariant().is(HAPaintingTags.INSTANCE.getKEEPS_PAINTING_VARIANT()))
            stack.set(DataComponents.PAINTING_VARIANT, getVariant());
        return stack;
    }
}
