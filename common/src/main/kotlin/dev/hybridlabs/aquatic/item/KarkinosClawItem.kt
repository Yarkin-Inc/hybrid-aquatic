package dev.hybridlabs.aquatic.item

import dev.hybridlabs.aquatic.CommonClass
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemAttributeModifiers

class KarkinosClawItem(settings: Properties) : Item(settings.attributes(
    ItemAttributeModifiers.builder().add(Attributes.BLOCK_INTERACTION_RANGE,
        AttributeModifier(CommonClass.locate("reach_modifier"), 3.0, AttributeModifier.Operation.ADD_VALUE),
        EquipmentSlotGroup.OFFHAND).build()))
