package dev.hybridlabs.aquatic.world.gen.structure

import dev.hybridlabs.aquatic.entity.HAEntityTypes
import net.minecraft.util.random.Weighted
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.structure.BuiltinStructures
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType

val BuiltinSpawnModifiers: Set<SpawnModifier> = setOf(
    SpawnModifier(
        "shipwreck", BuiltinStructures.SHIPWRECK, BoundingBoxType.STRUCTURE, mapOf(
            "shark" to listOf(
                Weighted(MobSpawnSettings.SpawnerData(HAEntityTypes.GREAT_WHITE_SHARK.get(), UniformInt.of(1, 3)), 1)
            )
        )
    ),
    SpawnModifier(
        "shipwreck_beached", BuiltinStructures.SHIPWRECK_BEACHED, BoundingBoxType.STRUCTURE, mapOf(
            "crustacean" to listOf(
                Weighted(MobSpawnSettings.SpawnerData(HAEntityTypes.COCONUT_CRAB.get(), UniformInt.of(1, 4)), 1)
            )
        )
    )
)