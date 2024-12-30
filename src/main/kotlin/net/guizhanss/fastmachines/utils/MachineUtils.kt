package net.guizhanss.fastmachines.utils

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.fastmachines.utils.items.summarize

fun BlockMenu.getItems(vararg slots: Int) = getItems(slots.toList())

fun BlockMenu.getItems(slots: List<Int>) = slots.mapNotNull { getItemInSlot(it) }.toList()

fun BlockMenu.countItems(vararg slots: Int) = countItems(slots.toList())

fun BlockMenu.countItems(slots: List<Int>) = getItems(slots).summarize()
