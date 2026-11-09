package dev.hybridlabs.aquatic.utils;

import com.mojang.serialization.MapCodec;
import dev.hybridlabs.aquatic.CommonClass;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.RegistryOps;

/** Trade entries and pools live in data; this condition preserves the config toggle. */
public final class HACustomTrades {
    private static boolean enabled;
    private static final Enabled CONDITION = new Enabled();
    private static final ResourceConditionType<Enabled> TYPE = ResourceConditionType.create(
        CommonClass.locate("trades_enabled"), MapCodec.unit(CONDITION));

    public static void registerCustomTrades(boolean enable) {
        enabled = enable;
        ResourceConditions.register(TYPE);
    }

    private static final class Enabled implements ResourceCondition {
        @Override public ResourceConditionType<?> getType() { return TYPE; }
        @Override public boolean test(RegistryOps.RegistryInfoLookup lookup) { return enabled; }
    }
}
