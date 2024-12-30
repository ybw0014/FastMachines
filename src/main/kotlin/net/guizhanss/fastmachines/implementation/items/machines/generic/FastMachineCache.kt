package net.guizhanss.fastmachines.implementation.items.machines.generic

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine.Companion.INPUT_SLOTS
import net.guizhanss.fastmachines.utils.countItems

class FastMachineCache(
    private val machine: AbstractFastMachine,
    private val menu: BlockMenu,
) {

    private val pos = BlockPosition(menu.location)
    private var page = -1
    private var invChecksum = 0

    fun tick() {
        if (!menu.hasViewer()) return

        val inputs = menu.countItems(*INPUT_SLOTS)
        if (inputs.hashCode() == invChecksum) return
        invChecksum = inputs.hashCode()

        FastMachines.debug("Ticking ${machine.javaClass.simpleName} at $pos")
        FastMachines.debug("Inputs: $inputs")
    }
}
