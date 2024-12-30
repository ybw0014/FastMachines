package net.guizhanss.fastmachines.core.services

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.utils.clamp
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig

class ConfigService(plugin: FastMachines) {

    var autoUpdate = true
        private set
    var debug = false
        private set
    var lang = "en"
        private set
    var enableResearches = true
        private set

    // fast machines options
    var fmTickRate = 10
        private set
    var fmUseEnergy = true
        private set

    private val config = AddonConfig(plugin, "config.yml")

    init {
        reload()
    }

    fun reload() {
        config.reload()

        autoUpdate = config.getBoolean("auto-update", true)
        debug = config.getBoolean("debug", false)
        lang = config.getString("lang") ?: "en"
        enableResearches = config.getBoolean("enable-researches", true)
        fmTickRate = config.getInt("fast-machines.tick-rate", 10).clamp(5, 600)
        fmUseEnergy = config.getBoolean("fast-machines.use-energy", true)

        config.save()
    }
}
