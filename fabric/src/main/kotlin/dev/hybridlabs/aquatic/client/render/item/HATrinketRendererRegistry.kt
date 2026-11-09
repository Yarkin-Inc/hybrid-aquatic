package dev.hybridlabs.aquatic.client.render.item

import eu.pb4.trinkets.api.client.TrinketRendererRegistry
import dev.hybridlabs.aquatic.item.HAItems
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item

object HATrinketRendererRegistry {

    init {
        registerTrinketRenderer(HAItems.EEL_SCARF.get(), EquipmentSlot.CHEST)
        registerTrinketRenderer(HAItems.STRIPED_EEL_SCARF.get(), EquipmentSlot.CHEST)
        registerTrinketRenderer(HAItems.MANGLERFISH_FIN.get(), EquipmentSlot.CHEST)
        registerTrinketRenderer(HAItems.MOON_JELLYFISH_HAT.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.MANGLERFISH_LURE.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.PINK_HATXOLOTL.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.GOLD_HATXOLOTL.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.BROWN_HATXOLOTL.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.CYAN_HATXOLOTL.get(), EquipmentSlot.HEAD)
        registerTrinketRenderer(HAItems.BLUE_HATXOLOTL.get(), EquipmentSlot.HEAD)
    }

    private fun registerTrinketRenderer(item: Item, equipmentSlot: EquipmentSlot) {
        TrinketRendererRegistry.registerRenderer(item, GeoTrinketRenderer(equipmentSlot))
    }
}
