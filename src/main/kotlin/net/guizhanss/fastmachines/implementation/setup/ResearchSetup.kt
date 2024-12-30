package net.guizhanss.fastmachines.implementation.setup

import io.github.thebusybiscuit.slimefun4.api.researches.Research
import net.guizhanss.fastmachines.implementation.items.FMItems
import net.guizhanss.fastmachines.utils.constants.Keys

object ResearchSetup {

    val MATERIALS = Research(
        Keys.MATERIALS,
        Keys.MATERIALS.hashCode(),
        "Fast Machine Materials",
        4
    )

    val MACHINES = Research(
        Keys.MACHINES,
        Keys.MACHINES.hashCode(),
        "Fast Machines",
        40
    )

    init {
        MATERIALS.addItems(
            FMItems.ETERNAL_FIRE,
            FMItems.FAST_CORE,
            FMItems.STACKED_ANCIENT_PEDESTAL,
        )

//        MACHINES.addItems(
//            FMItems.FAST_CRAFTING_TABLE,
//            FMItems.FAST_ENHANCED_CRAFTING_TABLE,
//            FMItems.FAST_ORE_WASHER,
//        )
    }
}
