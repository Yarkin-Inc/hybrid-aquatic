package dev.hybridlabs.aquatic.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

/** Separates a placement algorithm from its immutable, data-driven configuration. */
public abstract class FeatureAlgorithm<C> {
    private final MapCodec<Configured<C>> codec;

    protected FeatureAlgorithm(Codec<C> configurationCodec) {
        // Preserve this mod's existing "config" object in its feature JSONs.
        this.codec = configurationCodec.fieldOf("config").xmap(
                config -> new Configured<>(this, config), Configured::config);
    }

    public final MapCodec<Configured<C>> codec() { return codec; }
    public abstract boolean place(FeatureContext<C> context);

    protected void markAboveForPostProcessing(WorldGenLevel level, BlockPos origin) {
        BlockPos.MutableBlockPos pos = origin.mutable();
        for (int i = 0; i < 2; i++) {
            pos.move(Direction.UP);
            if (level.getBlockState(pos).isAir()) return;
            level.getChunk(pos).markPosForPostProcessing(pos);
        }
    }

    public record Configured<C>(FeatureAlgorithm<C> algorithm, C config) implements Feature {
        @Override public MapCodec<? extends Feature> codec() { return algorithm.codec(); }
        @Override public boolean place(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos pos) {
            return algorithm.place(new FeatureContext<>(level, generator, random, pos, config));
        }
    }
}
