package dev.hybridlabs.aquatic.world.gen.feature;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import dev.hybridlabs.aquatic.world.gen.feature.config.NoneFeatureConfiguration;
import dev.hybridlabs.aquatic.world.gen.feature.config.ProbabilityFeatureConfiguration;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

/** Checks preservation of existing config JSONs and isolation between configured feature instances. */
public final class FeatureCodecTest {
    public static void main(String[] args) {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var algorithm = new FeatureAlgorithm<ProbabilityFeatureConfiguration>(ProbabilityFeatureConfiguration.CODEC) {
            @Override public boolean place(FeatureContext<ProbabilityFeatureConfiguration> context) {
                return context.config().probability > 0.5f;
            }
        };
        var codec = algorithm.codec().codec();
        var lowJson = JsonParser.parseString("{\"config\":{\"probability\":0.25}}");
        var highJson = JsonParser.parseString("{\"config\":{\"probability\":0.75}}");
        var low = codec.parse(JsonOps.INSTANCE, lowJson).getOrThrow();
        var high = codec.parse(JsonOps.INSTANCE, highJson).getOrThrow();
        if (low.place(null, null, null, null) || !high.place(null, null, null, null)) {
            throw new AssertionError("Configured instances shared mutable parameters");
        }
        if (low.codec() != algorithm.codec() || high.codec() != algorithm.codec()) {
            throw new AssertionError("Configured feature lost its registered codec");
        }
        if (!codec.encodeStart(JsonOps.INSTANCE, low).getOrThrow().equals(lowJson)) {
            throw new AssertionError("Probability feature did not round-trip");
        }
        if (codec.parse(JsonOps.INSTANCE, JsonParser.parseString("{\"config\":{\"probability\":1.5}}")).error().isEmpty()) {
            throw new AssertionError("Invalid probabilities must be rejected");
        }
        var empty = new FeatureAlgorithm<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
            @Override public boolean place(FeatureContext<NoneFeatureConfiguration> context) { return true; }
        };
        var emptyJson = JsonParser.parseString("{\"config\":{}}");
        var decoded = empty.codec().codec().parse(JsonOps.INSTANCE, emptyJson).getOrThrow();
        if (!empty.codec().codec().encodeStart(JsonOps.INSTANCE, decoded).getOrThrow().equals(emptyJson)) {
            throw new AssertionError("Empty feature config did not round-trip");
        }
        System.out.println("Feature codec tests passed: round trips, instance isolation, validation, codec identity");
    }
}
