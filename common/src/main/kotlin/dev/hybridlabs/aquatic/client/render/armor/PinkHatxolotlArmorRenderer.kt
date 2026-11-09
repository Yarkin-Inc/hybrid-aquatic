package dev.hybridlabs.aquatic.client.render.armor

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.item.cosmetic.PinkHatxolotlArmorItem
import com.geckolib.model.DefaultedItemGeoModel
import com.geckolib.renderer.GeoArmorRenderer

class PinkHatxolotlArmorRenderer : GeoArmorRenderer<PinkHatxolotlArmorItem, net.minecraft.client.renderer.entity.state.HumanoidRenderState>(
    DefaultedItemGeoModel(CommonClass.locate("armor/pink_hatxolotl_armor"))
)