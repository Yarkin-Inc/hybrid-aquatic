package dev.hybridlabs.aquatic.client.weather;

import dev.hybridlabs.hapi.tag.HAPIBiomeTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

/** Uses vanilla weather buffers and OIT pipelines for the underwater marine snow. */
public final class MarineSnowRenderer extends WeatherEffectRenderer {
    public static WeatherRenderState extract(ClientLevel level, float partialTick, Vec3 camera) {
        WeatherRenderState state = new WeatherRenderState();
        float rain = level.getRainLevel(partialTick);
        if (rain <= 0 || camera.y >= level.getSeaLevel() ||
            !level.getBiome(BlockPos.containing(camera)).is(HAPIBiomeTags.INSTANCE.getALL_TRENCHES())) return state;
        state.intensity = rain * Mth.clamp((float)(level.getSeaLevel() - camera.y) / 48f, 0f, 0.8f);
        state.radius = Math.min(10, Minecraft.getInstance().options.weatherRadius().get());
        if (state.radius <= 0) return state;
        int cx = Mth.floor(camera.x), cy = Mth.floor(camera.y), cz = Mth.floor(camera.z);
        float time = (level.getGameTime() & 131071L) + partialTick;
        for (int z = cz - state.radius; z <= cz + state.radius; z++) {
            for (int x = cx - state.radius; x <= cx + state.radius; x++) {
                int floor = level.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z);
                int low = Math.max(cy - state.radius, floor);
                int high = Math.min(level.getSeaLevel(), Math.max(cy + state.radius, floor));
                if (low >= high) continue;
                RandomSource random = RandomSource.create((long)x * x * 3121 + x * 45238971L ^ (long)z * z * 418711 + z * 13761L);
                float u = (float)(random.nextDouble() + time * 0.001 * random.nextGaussian());
                float v = -time / 768f + (float)(random.nextDouble() + time * 0.0001 * random.nextGaussian());
                int light = LightCoordsUtil.getLightCoords(level, new BlockPos(x, Math.max(cy, floor), z));
                light = LightCoordsUtil.pack((LightCoordsUtil.block(light) * 3 + 15) / 4, (LightCoordsUtil.sky(light) * 3 + 15) / 4);
                state.snowColumns.add(new ColumnInstance(x, z, low, high, u, v, light));
            }
        }
        return state;
    }
}
