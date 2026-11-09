package dev.hybridlabs.aquatic.item.instrument

import dev.hybridlabs.aquatic.CommonClass
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Instrument

object HAInstruments {
    val OMINOUS_CONCH_HORN: ResourceKey<Instrument> = ResourceKey.create(
        Registries.INSTRUMENT, CommonClass.locate("ominous_conch_horn"))
}
