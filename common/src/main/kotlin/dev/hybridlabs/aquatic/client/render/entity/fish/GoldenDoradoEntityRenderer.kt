package dev.hybridlabs.aquatic.client.render.entity.fish

import com.mojang.blaze3d.vertex.PoseStack
import dev.hybridlabs.aquatic.client.model.entity.fish.GoldenDoradoEntityModel
import dev.hybridlabs.aquatic.entity.fish.GoldenDoradoEntity
import dev.hybridlabs.hapi.client.render.entity.aquatic.BaseFishEntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context
import net.minecraft.world.item.ItemStack
import com.geckolib.cache.model.GeoBone

class GoldenDoradoEntityRenderer(context: Context) :
    BaseFishEntityRenderer<GoldenDoradoEntity>(context, GoldenDoradoEntityModel(), true, false) {

    override fun getHeldItemRotation(): Float = 0f
}