package dev.hybridlabs.aquatic.test;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.nio.file.Files;
import java.nio.file.Path;

/** Opt-in registry and chunk-generation audit; never included in the release JAR. */
final class WwooCompatibilityAudit {
    static void run(MinecraftServer server) {
        var report = new JsonObject();
        var level = server.overworld();
        report.addProperty("seed", level.getSeed());
        var packs = new JsonArray();
        server.getPackRepository().getSelectedIds().forEach(packs::add);
        report.add("selected_packs", packs);
        var ops = RegistryOps.create(JsonOps.INSTANCE, server.registryAccess());
        var biomes = new JsonObject();
        var registry = server.registryAccess().lookupOrThrow(Registries.BIOME);
        registry.listElements().forEach(holder -> biomes.add(holder.key().identifier().toString(),
                Biome.DIRECT_CODEC.encodeStart(ops, holder.value()).getOrThrow()));
        report.add("biomes", biomes);
        var features = new JsonObject();
        var placed = server.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
        placed.listElements().filter(holder -> holder.key().identifier().getNamespace().equals("minecraft"))
                .forEach(holder -> features.add(holder.key().identifier().toString(),
                        PlacedFeature.DIRECT_CODEC.encodeStart(ops, holder.value()).getOrThrow()));
        report.add("vanilla_placed_features", features);
        var samples = new JsonArray();
        report.add("generated_samples", samples);
        write(report);
        for (String id : new String[] {"minecraft:deep_ocean", "minecraft:warm_ocean", "minecraft:river",
                "minecraft:beach", "hybrid_aquatic:trench", "hybrid_aquatic:coral_reef",
                "hybrid_aquatic:tropical_river", "hybrid_aquatic:tide_pools"}) {
            var found = level.findClosestBiome3d(holder -> holder.unwrapKey().orElseThrow().identifier().toString().equals(id),
                    new BlockPos(0, 40, 0), 3200, 32, 32);
            var sample = new JsonObject();
            sample.addProperty("target_biome", id);
            if (found == null) {
                sample.addProperty("found", false);
            } else {
                sample.addProperty("found", true);
                var pos = found.getFirst();
                sample.addProperty("x", pos.getX());
                sample.addProperty("y", pos.getY());
                sample.addProperty("z", pos.getZ());
                for (int dz = -1; dz <= 1; dz++) for (int dx = -1; dx <= 1; dx++)
                    level.getChunk((pos.getX() >> 4) + dx, (pos.getZ() >> 4) + dz);
                sample.addProperty("chunks_generated", 9);
                sample.addProperty("actual_biome", level.getBiome(pos).unwrapKey().orElseThrow().identifier().toString());
            }
            samples.add(sample);
            write(report);
            System.out.println("WWOO_AUDIT " + sample);
        }
    }

    private static void write(JsonObject report) {
        try {
            var output = Path.of(System.getenv("HA_WWOO_AUDIT_REPORT"));
            Files.createDirectories(output.getParent());
            Files.writeString(output, new GsonBuilder().setPrettyPrinting().create().toJson(report));
        } catch (java.io.IOException e) { throw new RuntimeException(e); }
    }
}
