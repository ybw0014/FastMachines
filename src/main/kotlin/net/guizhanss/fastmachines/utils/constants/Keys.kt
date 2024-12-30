package net.guizhanss.fastmachines.utils.constants

import net.guizhanss.fastmachines.FastMachines
import org.bukkit.NamespacedKey

object Keys {

    val MAIN_GROUP = get("fast_machines")
    val MATERIALS = get("materials")
    val MACHINES = get("machines")
    val DISPLAY_ITEM = get("display_item")

    fun get(key: String) =
        NamespacedKey(FastMachines.instance, key)
}
