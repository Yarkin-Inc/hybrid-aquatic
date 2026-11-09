package dev.hybridlabs.aquatic.client.model.entity.misc

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.entity.projectile.StarfishProjectileEntity
import net.minecraft.resources.Identifier
import dev.hybridlabs.hapi.client.model.HybridGeoModel as GeoModel

class StarfishProjectileEntityModel :
    GeoModel<StarfishProjectileEntity>() {

    override fun getModelResource(entity: StarfishProjectileEntity): Identifier {
        return CommonClass.locate("misc/starfish_projectile")
    }

    override fun getTextureResource(entity: StarfishProjectileEntity): Identifier {
        return CommonClass.locate("textures/entity/misc/starfish_projectile/starfish_projectile.png")
    }

    override fun getAnimationResource(entity: StarfishProjectileEntity): Identifier {
        return CommonClass.locate("entity/misc/starfish_projectile")
    }
}