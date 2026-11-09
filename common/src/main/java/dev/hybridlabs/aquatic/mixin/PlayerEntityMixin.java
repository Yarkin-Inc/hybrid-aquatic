package dev.hybridlabs.aquatic.mixin;

import com.google.common.collect.ImmutableList;
import dev.hybridlabs.aquatic.access.CustomPlayerEntityData;
import dev.hybridlabs.aquatic.effect.HAMobEffects;

import dev.hybridlabs.aquatic.item.HAItems;
import dev.hybridlabs.hapi.item.HAPIToolMaterials;
import dev.hybridlabs.hapi.entity.base.aquatic.BaseSharkEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import dev.hybridlabs.aquatic.tag.HAItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends Entity implements CustomPlayerEntityData {

    public PlayerEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean isSwimming();

    @Unique
    private int haHurtTime = 0;

    @Unique
    private boolean isHoldingDivingWeight;

    @Override
    public void setHybridHurtTime(int value) {
        haHurtTime = value;
    }

    @Override
    public int getHybridHurtTime() {
        return haHurtTime;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readCustomDataFromNbt(net.minecraft.world.level.storage.ValueInput nbt, CallbackInfo ci) {
        setHybridHurtTime(nbt.getIntOr("haHurtTime", 0));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeCustomDataToNbt(net.minecraft.world.level.storage.ValueOutput nbt, CallbackInfo ci) {
        nbt.putInt("haHurtTime", getHybridHurtTime());
    }

    @Inject(method = "isAffectedByFluids", at = @At("HEAD"), cancellable = true)
    private void overrideShouldSwimInFluids(CallbackInfoReturnable<Boolean> ci) {
        if ((isHoldingDivingWeight) && !isSwimming() && isUnderWater()) {
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void setCustomHurtTimeOnDamage(ServerLevel server, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;
        Player object = (Player) (Object) this;

        if (object.isInWater()) {
            LivingEntity foundEntity =
                    server
                            .getNearestEntity(
                                    BaseSharkEntity.class,
                                    TargetingConditions.forNonCombat()
                                            .range(32)
                                            .selector((entity, level) -> entity.isUnderWater()),
                                    object,
                                    object.getX(),
                                    object.getEyeY(),
                                    object.getZ(),
                                    object.getBoundingBox().inflate(16));
            if (foundEntity != null) setHybridHurtTime(200);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickDownCustomHurtTime(CallbackInfo ci) {
        int cHurtTime = getHybridHurtTime();
        if (cHurtTime > 0) {
            setHybridHurtTime(cHurtTime - 1);
        }
        updateDivingWeight();
        if (level().isClientSide()) return;
        // Gives Water Breathing/Clarity if player has Diving Helmet equipped
        updateDivingHelmet();
        // Gives Resistance and Slowness if player has Turtle chestplate equipped
        updateTurtleChestplate();
        // Repairs coral tools in the water
        repairCoralTools();
    }@Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void hybridaquatic$boostSeashellUnderwaterSpeed(
            BlockState state, CallbackInfoReturnable<Float> cir
    ) {
        Player player = (Player)(Object)this;

        ItemStack stack = player.getMainHandItem();
        if (!stack.is(HAItemTags.INSTANCE.getSEASHELL_SET()) || !stack.has(net.minecraft.core.component.DataComponents.TOOL)) return;

        if (player.isEyeInFluid(FluidTags.WATER)) {
            var aquaAffinity = player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(Enchantments.AQUA_AFFINITY);

            boolean hasAquaAffinity =
                    EnchantmentHelper.getEnchantmentLevel(aquaAffinity, player) > 0;

            float speed = cir.getReturnValue();

            if (!hasAquaAffinity) {
                speed *= 5.0F;
            }

            cir.setReturnValue(speed);
        }
    }

    @Unique
    private void updateDivingHelmet() {
        var player = (Player) (Object) this;
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);

        if (itemStack.is(HAItems.INSTANCE.getDIVING_HELMET().get())) {
            if (!player.isEyeInFluid(FluidTags.WATER)) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.WATER_BREATHING, 600, 0, false, false, false));
            } else {
                player.addEffect(
                        new MobEffectInstance(
                                HAMobEffects.INSTANCE.getCLARITY().asHolder(),
                                600,
                                0,
                                false,
                                false,
                                false));
            }
        }

        if (itemStack.is(HAItems.INSTANCE.getREINFORCED_DIVING_HELMET().get())) {
            if (!player.isEyeInFluid(FluidTags.WATER)) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.WATER_BREATHING, 1800, 0, false, false, false));
            } else {
                player.addEffect(
                        new MobEffectInstance(
                                HAMobEffects.INSTANCE.getCLARITY().asHolder(),
                                1800,
                                0,
                                false,
                                false,
                                false));
            }
        }

        if (itemStack.is(HAItems.INSTANCE.getGLOWING_DIVING_HELMET().get())) {
            if (!player.isEyeInFluid(FluidTags.WATER)) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.WATER_BREATHING, 1800, 0, false, false, false));
            } else {
                player.addEffect(
                        new MobEffectInstance(
                                HAMobEffects.INSTANCE.getCLARITY().asHolder(),
                                1800,
                                0,
                                false,
                                false,
                                false));
            }
        }
    }

    @Unique
    private void updateDivingWeight() {
        var player = (Player) (Object) this;

        isHoldingDivingWeight =
                player.getMainHandItem().is(HAItems.INSTANCE.getDIVING_WEIGHT().get()) ||
                        player.getOffhandItem().is(HAItems.INSTANCE.getDIVING_WEIGHT().get());
    }

    @Unique
    private void updateTurtleChestplate() {
        var player = (Player) (Object) this;
        var itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack.is(HAItems.INSTANCE.getTURTLE_CHESTPLATE().get())) {
            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.RESISTANCE, 200, 0, false, false, true));
            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.SLOWNESS, 200, 0, false, false, true));
        }
    }

    @Unique
    int coralRepairTick = 0;

    @Unique
    private void repairCoralTools() {
        var player = (Player) (Object) this;
        var inventory = player.getInventory();

        if (player.isEyeInFluid(FluidTags.WATER)) {
            if (coralRepairTick > 5) {
                List<ItemStack> coralItems = new ArrayList<>();
                for (ItemStack itemStack : inventory) {
                    if (itemStack.is(HAItemTags.INSTANCE.getCORAL_SET()) && itemStack.isDamaged()) coralItems.add(itemStack);
                }

                if (!coralItems.isEmpty()) {
                    ItemStack item = coralItems.get(player.getRandom().nextInt(coralItems.size()));
                    item.setDamageValue(item.getDamageValue() - 1);
                    inventory.setChanged();
                }
                coralRepairTick = 0;
            }

            coralRepairTick++;
        }
    }
}
