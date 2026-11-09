@file:Suppress("UNUSED_PARAMETER")

package dev.hybridlabs.aquatic

import dev.hybridlabs.aquatic.block.HABlocks
import dev.hybridlabs.aquatic.block.HAPlatformBlocks
import dev.hybridlabs.aquatic.block.entity.HABlockEntityTypes
import dev.hybridlabs.aquatic.client.data.HypnoticEntities
import dev.hybridlabs.aquatic.client.gui.screen.HAMenuScreens
import dev.hybridlabs.aquatic.client.item.HAItemProperties
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.BASKING_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.BULL_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.FRILLED_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.GREAT_WHITE_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.HAMMERHEAD_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.THRESHER_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.TIGER_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.HAEntityModelLayers.WHALE_SHARK_PLUSHIE
import dev.hybridlabs.aquatic.client.model.block.entity.plushie.*
import dev.hybridlabs.aquatic.client.network.HybridAquaticClientNetworking
import dev.hybridlabs.aquatic.client.render.block.HABlockRendererRegistry
import dev.hybridlabs.aquatic.client.render.block.HAModelLayerRegistry
import dev.hybridlabs.aquatic.client.render.block.entity.*
import dev.hybridlabs.aquatic.client.render.entity.HybridAquaticEntityRenderers
import dev.hybridlabs.aquatic.client.render.fluid.HAFluidRenderer
import dev.hybridlabs.aquatic.client.render.item.HATrinketRendererRegistry
import dev.hybridlabs.aquatic.client.render.particle.HAParticleRendererRegistry
import dev.hybridlabs.aquatic.platform.ClientServices
import dev.hybridlabs.aquatic.platform.Services
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry.registerModelLayer
import net.minecraft.client.renderer.chunk.ChunkSectionLayer
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component

@Suppress("UnusedExpression", "DEPRECATION")
object HybridAquaticClient : ClientModInitializer {
    override fun onInitializeClient() {
        HAEntityModelLayers
        HybridAquaticClientNetworking

        HABlockRendererRegistry
        HAParticleRendererRegistry
        HybridAquaticEntityRenderers
        registerWeatherRenderers()
        if (Services.PLATFORM.isModLoaded("trinkets")) HATrinketRendererRegistry
        HAModelLayerRegistry
        HAItemProperties
        HAFluidRenderer()

        HAMenuScreens

        ItemTooltipCallback.EVENT.register { stack, _, _, lines ->
            if (BuiltInRegistries.ITEM.getKey(stack.item).namespace == Constants.MOD_ID && lines.isNotEmpty()) {
                // Keep custom names, item descriptions and advanced debug information intact.
                if (lines.drop(1).none { it.string == Constants.MOD_NAME }) {
                    lines.add(1, Component.literal(Constants.MOD_NAME).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC))
                }
            }
        }

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            HypnoticEntities.mobs.clear()
        }
    }

    private fun registerWeatherRenderers() {
        // TODO: hook up renderer to make this thing easier
    }

    private fun registerRenderShapes() {
    }

    private fun registerBlockEntityRenderers() {
        BlockEntityRenderers.register(HABlockEntityTypes.ANEMONE.get(), ::AnemoneBlockEntityRenderer)
        BlockEntityRenderers.register(
            HABlockEntityTypes.GIANT_GREEN_ANEMONE.get(),
            ::GiantGreenAnemoneBlockEntityRenderer
        )
        BlockEntityRenderers.register(
            HABlockEntityTypes.STRAWBERRY_ANEMONE.get(),
            ::StrawberryAnemoneBlockEntityRenderer
        )
        BlockEntityRenderers.register(
            HABlockEntityTypes.MESSAGE_IN_A_BOTTLE.get(),
            ::MessageInABottleBlockEntityRenderer
        )
        BlockEntityRenderers.register(HABlockEntityTypes.BUOY.get(), ::BuoyBlockEntityRenderer)
    }

    private fun registerEntityRenderers() {
        HybridAquaticEntityRenderers
    }

    private fun registerModelLayers() {
        registerModelLayer(BASKING_SHARK_PLUSHIE, BaskingSharkPlushieModel.Companion::createModelData)
        registerModelLayer(BULL_SHARK_PLUSHIE, BullSharkPlushieModel.Companion::createModelData)
        registerModelLayer(FRILLED_SHARK_PLUSHIE, FrilledSharkPlushieModel.Companion::createModelData)
        registerModelLayer(GREAT_WHITE_SHARK_PLUSHIE, GreatWhiteSharkPlushieModel.Companion::createModelData)
        registerModelLayer(HAMMERHEAD_SHARK_PLUSHIE, HammerheadSharkPlushieModel.Companion::createModelData)
        registerModelLayer(THRESHER_SHARK_PLUSHIE, ThresherSharkPlushieModel.Companion::createModelData)
        registerModelLayer(TIGER_SHARK_PLUSHIE, TigerSharkPlushieModel.Companion::createModelData)
        registerModelLayer(WHALE_SHARK_PLUSHIE, WhaleSharkPlushieModel.Companion::createModelData)
    }
}
