package dev.hybridlabs.aquatic.mixin.client;
import dev.hybridlabs.aquatic.client.weather.MarineSnowStateAccess;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
@Mixin(WeatherRenderState.class)
public class WeatherRenderStateMixin implements MarineSnowStateAccess {
    @Unique private WeatherRenderState hybrid$marineSnow;
    public WeatherRenderState hybrid$getMarineSnow() { return hybrid$marineSnow; }
    public void hybrid$setMarineSnow(WeatherRenderState state) { hybrid$marineSnow = state; }
}
