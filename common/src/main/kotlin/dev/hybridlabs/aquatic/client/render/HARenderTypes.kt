package dev.hybridlabs.aquatic.client.render

import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.RenderTypes
import net.minecraft.resources.Identifier

/** Shared pipelines; render setup is owned by Minecraft/GeckoLib in 26.3. */
object HARenderTypes {
    @Suppress("UNUSED_PARAMETER")
    fun getSubmarineLights(texture: Identifier): RenderType = RenderTypes.lightning()

    @Suppress("UNUSED_PARAMETER")
    fun getSubmarineMask(texture: Identifier): RenderType = RenderTypes.waterMask()

    fun getEyesAlphaEnabled(texture: Identifier): RenderType =
        AutoGlowingGeoLayer.EmissiveRenderType.getRenderType(texture, true, true, false)
}
