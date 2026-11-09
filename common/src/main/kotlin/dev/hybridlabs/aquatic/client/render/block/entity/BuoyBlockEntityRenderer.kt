package dev.hybridlabs.aquatic.client.render.block.entity

import dev.hybridlabs.aquatic.block.entity.BuoyBlockEntity
import dev.hybridlabs.aquatic.client.model.block.entity.BuoyBlockEntityModel
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import dev.hybridlabs.hapi.client.render.HybridGeoBlockRenderer as GeoBlockRenderer
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer

class BuoyBlockEntityRenderer(context: BlockEntityRendererProvider.Context) :
    GeoBlockRenderer<BuoyBlockEntity>(context, BuoyBlockEntityModel()) {
    init {
        withRenderLayer(AutoGlowingGeoLayer(this))
    }
}