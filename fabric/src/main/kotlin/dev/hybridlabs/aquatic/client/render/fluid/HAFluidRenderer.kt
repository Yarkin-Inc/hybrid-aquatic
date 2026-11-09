package dev.hybridlabs.aquatic.client.render.fluid

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.fluid.HAPlatformFluids
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry
import net.minecraft.client.renderer.block.FluidModel
import net.minecraft.client.resources.model.sprite.Material

class HAFluidRenderer {
    init {
        FluidRenderingRegistry.register(HAPlatformFluids.BRINE_STILL.get(), HAPlatformFluids.BRINE_FLOWING.get(),
            FluidModel.Unbaked(Material(CommonClass.locate("block/brine_still")),
                Material(CommonClass.locate("block/brine_flowing")),
                Material(CommonClass.locate("block/brine_overlay")), { -1 }))
    }
}
