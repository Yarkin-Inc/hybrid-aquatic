package dev.hybridlabs.aquatic.test;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.core.BlockPos;

public final class AquaticWorldTests implements FabricClientGameTest {
    @Override public void runTest(ClientGameTestContext context) {
        try (var world = context.worldBuilder().adjustSettings(settings -> {
            var preset = settings.getSettings().worldgenLoadContext().lookupOrThrow(Registries.WORLD_PRESET).getOrThrow(WorldPresets.NORMAL);
            settings.setWorldType(new WorldCreationUiState.WorldTypeEntry(preset));
        }).create()) {
            world.getServer().runOnServer(server -> {
                if (!(server.overworld().getChunkSource().getGenerator() instanceof NoiseBasedChunkGenerator))
                    throw new AssertionError("Normal noise world required");
                var level = server.overworld();
                if (System.getenv("HA_WWOO_AUDIT_REPORT") != null) WwooCompatibilityAudit.run(server);
                var trench = level.findClosestBiome3d(holder -> holder.unwrapKey().orElseThrow().identifier().getNamespace().equals("hybrid_aquatic")
                        && holder.unwrapKey().orElseThrow().identifier().getPath().contains("trench"), new BlockPos(0, 20, 0), 6400, 64, 32);
                if (trench == null) throw new AssertionError("No trench biome near the test seed");
                var pos = trench.getFirst();
                level.getChunk(pos);
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),
                        "tp @a " + pos.getX() + " 40 " + pos.getZ());
            });
            world.getServer().runCommand("gamemode spectator @a");
            world.getServer().runCommand("weather rain");
            world.getServer().runCommand("effect give @a hybrid_aquatic:clarity 20");
            context.waitTicks(30);
            context.takeScreenshot("aquatic-trench-world");
        }
    }
}
