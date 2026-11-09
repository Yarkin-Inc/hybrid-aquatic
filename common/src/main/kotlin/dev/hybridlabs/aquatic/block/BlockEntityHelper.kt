package dev.hybridlabs.aquatic.block

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class BlockEntityHelper {
    companion object {
        fun createBlockEntityRendererProviderContext(baking: net.minecraft.client.renderer.special.SpecialModelRenderer.BakingContext): BlockEntityRendererProvider.Context {
            val client =
                Minecraft.getInstance()
            return BlockEntityRendererProvider.Context(
                client.blockEntityRenderDispatcher,
                net.minecraft.client.renderer.block.BlockModelResolver(client.modelManager),
                client.itemModelResolver,
                client.entityRenderDispatcher,
                client.entityModels,
                client.font,
                baking.sprites(),
                baking.playerSkinRenderCache()
            )
        }
    }
}
