package net.guizhanss.fastmachines.utils.reflections

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

// backward compatibility, shaped and shapeless recipes extend crafting recipe in 1.20+
val Recipe.resultItem: ItemStack
    get() = (invoke("getResult") as? ItemStack)!!
