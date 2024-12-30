package net.guizhanss.fastmachines.implementation.listeners

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.slimefuntranslation.api.events.TranslationsLoadEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class TranslationListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onTranslationsLoad(e: TranslationsLoadEvent) {
        FastMachines.scheduler().runAsync { FastMachines.integrationService.loadTranslations() }
    }
}
