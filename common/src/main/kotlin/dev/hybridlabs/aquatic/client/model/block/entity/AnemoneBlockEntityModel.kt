package dev.hybridlabs.aquatic.client.model.block.entity

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.block.entity.AnemoneBlockEntity
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

@Suppress("OVERRIDE_DEPRECATION")
class AnemoneBlockEntityModel : GeoModel<AnemoneBlockEntity>() {
    override fun getAnimationResource(entity: AnemoneBlockEntity): Identifier {
        return ANIMATION_LOCATION
    }

    override fun getModelResource(animatable: AnemoneBlockEntity): Identifier {
        return MODEL_LOCATION
    }

    override fun getTextureResource(entity: AnemoneBlockEntity): Identifier {
        return TEXTURE_LOCATION
    }

    companion object {
        val ANIMATION_LOCATION: Identifier = CommonClass.locate("anemone")
        val MODEL_LOCATION: Identifier = CommonClass.locate("anemone")
        val TEXTURE_LOCATION: Identifier = CommonClass.locate("textures/block/anemone.png")
    }
}