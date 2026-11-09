package dev.hybridlabs.aquatic.item

import dev.hybridlabs.aquatic.CommonClass
import dev.hybridlabs.hapi.tag.HAPIItemTags
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.item.equipment.EquipmentAssets
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import java.util.*
import java.util.function.Supplier
import kotlin.collections.set
import kotlin.jvm.java
import kotlin.to

object HAArmorMaterials {
    val DIVING = register(
        "diving",
        mapOf(
            ArmorType.HELMET to 2,
            ArmorType.CHESTPLATE to 5,
            ArmorType.LEGGINGS to 4,
            ArmorType.BOOTS to 2,
            ArmorType.BODY to 5
        ),
        9,
        SoundEvents.ARMOR_EQUIP_CHAIN,
        HAPIItemTags.COPPER_INGOT,
        0.0f,
        0.0f,
    )

    val GLOWING_DIVING = register(
        "glowing_diving",
        mapOf(
            ArmorType.HELMET to 2,
            ArmorType.CHESTPLATE to 5,
            ArmorType.LEGGINGS to 4,
            ArmorType.BOOTS to 2,
            ArmorType.BODY to 5
        ),
        9,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        HAPIItemTags.COPPER_INGOT,
        0.0f,
        0.0f,
    )

    val REINFORCED_DIVING = register(
        "reinforced_diving",
        mapOf(
            ArmorType.HELMET to 3,
            ArmorType.CHESTPLATE to 7,
            ArmorType.LEGGINGS to 5,
            ArmorType.BOOTS to 3,
            ArmorType.BODY to 5
        ),
        9,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        HAPIItemTags.COPPER_INGOT,
        0.0f,
        1.0f,
    )

    val SEASHELL = register(
        "seashell",
        mapOf(
            ArmorType.HELMET to 2,
            ArmorType.CHESTPLATE to 4,
            ArmorType.LEGGINGS to 3,
            ArmorType.BOOTS to 2,
            ArmorType.BODY to 4
        ),
        22,
        SoundEvents.ARMOR_EQUIP_TURTLE,
        HAPIItemTags.NAUTILUS_SHELL,
        0.0f,
        0.0f,
    )

    val MANGLERFISH = register(
        "manglerfish",
        mapOf(
            ArmorType.HELMET to 1,
            ArmorType.CHESTPLATE to 1,
            ArmorType.LEGGINGS to 1,
            ArmorType.BOOTS to 1,
            ArmorType.BODY to 1
        ),
        15,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        HAPIItemTags.GLOWSLIME,
        0.0f,
        0.0f
    )

    val EEL = register(
        "eel",
        mapOf(
            ArmorType.HELMET to 1,
            ArmorType.CHESTPLATE to 1,
            ArmorType.LEGGINGS to 1,
            ArmorType.BOOTS to 1,
            ArmorType.BODY to 1
        ),
        15,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        ItemTags.WOOL,
        0.0f,
        0.0f
    )

    val HATXOLOTL = register(
        "hatxolotl",
        mapOf(
            ArmorType.HELMET to 1,
            ArmorType.CHESTPLATE to 1,
            ArmorType.LEGGINGS to 1,
            ArmorType.BOOTS to 1,
            ArmorType.BODY to 1
        ),
        15,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        HAPIItemTags.LEATHER,
        0.0f,
        0.0f
    )

    val MOONJELLYFISH = register(
        "moon_jelly",
        mapOf(
            ArmorType.HELMET to 1,
            ArmorType.CHESTPLATE to 1,
            ArmorType.LEGGINGS to 1,
            ArmorType.BOOTS to 1,
            ArmorType.BODY to 1
        ),
        15,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        HAPIItemTags.SLIME_BALL,
        0.0f,
        0.0f
    )

    val TURTLE = register(
        "turtle",
        mapOf(
            ArmorType.HELMET to 1,
            ArmorType.CHESTPLATE to 1,
            ArmorType.LEGGINGS to 1,
            ArmorType.BOOTS to 1,
            ArmorType.BODY to 1
        ),
        9,
        SoundEvents.ARMOR_EQUIP_TURTLE,
        HAPIItemTags.TURTLE_SCUTE,
        1.0f,
        0.3f,
    )

    private fun register(
        name: String,
        defense: Map<ArmorType, Int>,
        enchantmentValue: Int,
        equipSound: Holder<SoundEvent>,
        repairItems: TagKey<Item>,
        toughness: Float,
        knockbackResistance: Float
    ): ArmorMaterial = ArmorMaterial(
        if (name == "reinforced_diving" || name == "turtle") 25 else 15,
        defense, enchantmentValue, equipSound, toughness, knockbackResistance,
        repairItems, ResourceKey.create(EquipmentAssets.ROOT_ID, CommonClass.locate(name))
    )
}
