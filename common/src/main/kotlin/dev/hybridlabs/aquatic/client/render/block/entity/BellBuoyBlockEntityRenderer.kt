package dev.hybridlabs.aquatic.client.render.block.entity

import dev.hybridlabs.aquatic.block.entity.BellBuoyBlockEntity
import dev.hybridlabs.aquatic.client.model.block.entity.BellBuoyBlockEntityModel
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import dev.hybridlabs.hapi.client.render.HybridGeoBlockRenderer as GeoBlockRenderer

class BellBuoyBlockEntityRenderer(context: BlockEntityRendererProvider.Context) :
    GeoBlockRenderer<BellBuoyBlockEntity>(context, BellBuoyBlockEntityModel())