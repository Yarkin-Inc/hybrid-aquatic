package dev.hybridlabs.aquatic.client.render.armor

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.aquatic.item.armor.TurtleArmorItem
import com.geckolib.model.DefaultedItemGeoModel
import com.geckolib.renderer.GeoArmorRenderer

class TurtleArmorRenderer : GeoArmorRenderer<TurtleArmorItem, net.minecraft.client.renderer.entity.state.HumanoidRenderState>(
    DefaultedItemGeoModel(CommonClass.locate("armor/turtle_armor"))
)