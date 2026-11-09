package dev.hybridlabs.aquatic.client.render.armor

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.item.cosmetic.MoonJellyfishArmorItem
import com.geckolib.model.DefaultedItemGeoModel
import com.geckolib.renderer.GeoArmorRenderer

class MoonJellyfishArmorRenderer : GeoArmorRenderer<MoonJellyfishArmorItem, net.minecraft.client.renderer.entity.state.HumanoidRenderState>(
    DefaultedItemGeoModel(CommonClass.locate("armor/moon_jellyfish_armor"))
)