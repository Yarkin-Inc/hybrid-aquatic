package dev.hybridlabs.aquatic.client.render.armor

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.item.cosmetic.ManglerfishArmorItem
import com.geckolib.model.DefaultedItemGeoModel
import com.geckolib.renderer.GeoArmorRenderer

class ManglerfishArmorRenderer : GeoArmorRenderer<ManglerfishArmorItem, net.minecraft.client.renderer.entity.state.HumanoidRenderState>(
    DefaultedItemGeoModel(CommonClass.locate("armor/manglerfish_armor"))
)