package dev.hybridlabs.aquatic.client.model.block.entity

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.block.entity.GiantGreenAnemoneBlockEntity
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

@Suppress("OVERRIDE_DEPRECATION")
class GiantGreenAnemoneBlockEntityModel : GeoModel<GiantGreenAnemoneBlockEntity>() {
    override fun getAnimationResource(entity: GiantGreenAnemoneBlockEntity): Identifier {
        return ANIMATION_LOCATION
    }

    override fun getModelResource(animatable: GiantGreenAnemoneBlockEntity): Identifier {
        return MODEL_LOCATION
    }

    override fun getTextureResource(entity: GiantGreenAnemoneBlockEntity): Identifier {
        return TEXTURE_LOCATION
    }

    companion object {
        val ANIMATION_LOCATION: Identifier = CommonClass.locate("anemone")
        val MODEL_LOCATION: Identifier = CommonClass.locate("giant_green_anemone")
        val TEXTURE_LOCATION: Identifier = CommonClass.locate("textures/block/giant_green_anemone.png")
    }
}