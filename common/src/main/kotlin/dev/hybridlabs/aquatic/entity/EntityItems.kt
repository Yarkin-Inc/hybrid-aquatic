package dev.hybridlabs.aquatic.entity

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

/** Spawn drops only on the authoritative level, including calls shared by client interactions. */
fun Entity.spawnDrop(stack: ItemStack): ItemEntity? =
    (level() as? ServerLevel)?.let { spawnAtLocation(it, stack) }

fun Entity.spawnDrop(item: ItemLike): ItemEntity? =
    (level() as? ServerLevel)?.let { spawnAtLocation(it, item) }
