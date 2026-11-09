package dev.hybridlabs.aquatic.fog;

import net.minecraft.client.renderer.fog.FogData;
import dev.hybridlabs.aquatic.effect.HAMobEffects;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ClarityFogModifier {
    public @NotNull Holder<MobEffect> getMobEffect() {
        return HAMobEffects.INSTANCE.getCLARITY().asHolder();
    }

    public void setupFog(FogData fogData, @NotNull LivingEntity entity, MobEffectInstance effect,
                         float viewDistance, float tickDelta) {

        float fogDistanceMultiplier = 0.5f;

        float f = effect.isInfiniteDuration() ? 5.0F : Mth.lerp(Math.min(1.0F, (float) effect.getDuration() / 20.0F),
                viewDistance, 5.0F);
        fogData.environmentalStart = f * 24.0F * fogDistanceMultiplier;
        fogData.environmentalEnd = f * 48 * fogDistanceMultiplier;
        fogData.skyEnd = fogData.environmentalEnd;
        fogData.cloudEnd = fogData.environmentalEnd;
    }
}