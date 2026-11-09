package dev.hybridlabs.aquatic.world.gen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;

public record FeatureContext<C>(WorldGenLevel level, ChunkGenerator chunkGenerator,
                                RandomSource random, BlockPos origin, C config) {}
