package dev.hybridlabs.aquatic.client.render;

import com.geckolib.constant.dataticket.DataTicket;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class HARenderData {
    public static final DataTicket<Float> ARGONAUT_PITCH = DataTicket.create("hybrid_aquatic:argonaut_pitch", Float.class);
    public static final DataTicket<RenderType> BOBBER_LAYER = DataTicket.create("hybrid_aquatic:bobber_layer", RenderType.class);
    public static final DataTicket<Boolean> TRANSPARENT_PAINTING = DataTicket.create("hybrid_aquatic:transparent_painting", Boolean.class);
}
