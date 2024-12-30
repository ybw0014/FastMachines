package net.guizhanss.fastmachines.utils.items

import io.github.seggan.sf4k.extensions.getSlimefun
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import org.bukkit.World
import org.bukkit.inventory.ItemStack

fun ItemStack.isDisabled() =
    this.getSlimefun<SlimefunItem>()?.isDisabled == true

fun ItemStack.isDisabledIn(world: World) =
    this.getSlimefun<SlimefunItem>()?.isDisabledIn(world) == true
