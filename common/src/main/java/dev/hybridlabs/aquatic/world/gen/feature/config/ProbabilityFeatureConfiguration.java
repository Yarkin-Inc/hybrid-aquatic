package dev.hybridlabs.aquatic.world.gen.feature.config;

import com.mojang.serialization.Codec;

public final class ProbabilityFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<ProbabilityFeatureConfiguration> CODEC = Codec.floatRange(0, 1)
            .fieldOf("probability").xmap(ProbabilityFeatureConfiguration::new, c -> c.probability).codec();
    public final float probability;
    public ProbabilityFeatureConfiguration(float probability) { this.probability = probability; }
}
