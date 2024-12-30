package net.guizhanss.fastmachines.implementation.listeners

import net.guizhanss.fastmachines.FastMachines
import org.bukkit.event.Listener

class CauldronListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

}
