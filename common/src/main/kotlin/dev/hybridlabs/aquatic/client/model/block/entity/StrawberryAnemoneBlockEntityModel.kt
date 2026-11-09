package dev.hybridlabs.aquatic.client.model.block.entity

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.block.entity.StrawberryAnemoneBlockEntity
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

@Suppress("OVERRIDE_DEPRECATION")
class StrawberryAnemoneBlockEntityModel : GeoModel<StrawberryAnemoneBlockEntity>() {
    override fun getAnimationResource(entity: StrawberryAnemoneBlockEntity): Identifier {
        return ANIMATION_LOCATION
    }

    override fun getModelResource(animatable: StrawberryAnemoneBlockEntity): Identifier {
        return MODEL_LOCATION
    }

    override fun getTextureResource(entity: StrawberryAnemoneBlockEntity): Identifier {
        return TEXTURE_LOCATION
    }

    companion object {
        val ANIMATION_LOCATION: Identifier = CommonClass.locate("anemone")
        val MODEL_LOCATION: Identifier = CommonClass.locate("strawberry_anemone")
        val TEXTURE_LOCATION: Identifier = CommonClass.locate("textures/block/strawberry_anemone.png")
    }
}