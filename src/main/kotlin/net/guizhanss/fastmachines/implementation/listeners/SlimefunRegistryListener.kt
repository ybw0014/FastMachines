package net.guizhanss.fastmachines.implementation.listeners

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.FMRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.logging.Level

class SlimefunRegistryListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onRegistryLoaded(e: SlimefunItemRegistryFinalizedEvent) {
        for (machine in FMRegistry.enabledFastMachines) {
            FastMachines.debug("Registering recipes for ${machine.javaClass.simpleName}")
            try {
                machine.recipeLoader.load()
            } catch (ex: Exception) {
                FastMachines.log(
                    Level.SEVERE,
                    ex,
                    "An error has occurred while registering recipes for ${machine.javaClass.simpleName}",
                )
            }
        }
    }
}
