package dev.hybridlabs.aquatic.client.weather;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
public interface WeatherRendererAccess {
    void hybrid$renderWithPipeline(WeatherRenderState state, RenderPass pass, RenderPipeline pipeline);
}
