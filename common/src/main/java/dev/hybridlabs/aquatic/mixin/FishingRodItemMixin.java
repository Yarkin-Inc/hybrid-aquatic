package dev.hybridlabs.aquatic.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.hybridlabs.aquatic.access.CustomFishingBobberEntityData;
import dev.hybridlabs.aquatic.tag.HAItemTags;
import dev.hybridlabs.aquatic.utils.HandUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectile(Lnet/minecraft/world/entity/projectile/Projectile;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/Projectile;"), index = 0)
    private Projectile hybrid$attachLure(Projectile bobber, @Local(argsOnly = true) Player player,
                                         @Local(argsOnly = true) InteractionHand hand) {
        ItemStack lure = player.getItemInHand(HandUtils.getOpposingHand(hand));
        if (lure.is(HAItemTags.INSTANCE.getLURE_ITEMS()))
            ((CustomFishingBobberEntityData) bobber).setLureItem(lure.copyAndClear());
        return bobber;
    }
}
