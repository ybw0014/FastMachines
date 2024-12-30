package net.guizhanss.fastmachines.implementation.groups

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.utils.constants.HeadTexture
import net.guizhanss.fastmachines.utils.constants.Keys
import org.bukkit.Material

object FMItemGroups {

    val MAIN = NestedItemGroup(
        Keys.MAIN_GROUP,
        FastMachines.localization.getItem(
            "FAST_MACHINES",
            HeadTexture.MAIN.texture
        )
    )

    val MATERIALS = SubItemGroup(
        Keys.MATERIALS,
        MAIN,
        FastMachines.localization.getItem(
            "MATERIALS",
            Material.DIAMOND
        )
    )

    val MACHINES = SubItemGroup(
        Keys.MACHINES,
        MAIN,
        FastMachines.localization.getItem(
            "MACHINES",
            HeadTexture.MAIN.texture
        )
    )
}
