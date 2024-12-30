package net.guizhanss.fastmachines.core.services

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.implementation.listeners.TranslationListener
import net.guizhanss.slimefuntranslation.api.config.TranslationConfiguration
import net.guizhanss.slimefuntranslation.api.config.TranslationConfigurationDefaults
import net.guizhanss.slimefuntranslation.api.config.TranslationConfigurationFields
import net.guizhanss.slimefuntranslation.core.factories.MessageFactory
import net.guizhanss.slimefuntranslation.utils.FileUtils
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class IntegrationService(private val plugin: FastMachines) {

    val infinityExpansionEnabled = isEnabled("InfinityExpansion")
    val slimeFrameEnabled = isEnabled("SlimeFrame")
    val slimefunTranslationEnabled = isEnabled("SlimefunTranslation")

    init {
        if (slimefunTranslationEnabled) {
            TranslationListener(plugin)
        }
    }

    private fun isEnabled(pluginName: String): Boolean {
        return plugin.server.pluginManager.isPluginEnabled(pluginName)
    }

    fun loadTranslations() {
        val fields = TranslationConfigurationFields.builder().items("items").build()
        val defaults = TranslationConfigurationDefaults.builder().name("FastMachines").prefix("FM_").build()
        val languages = FileUtils.listYamlFiles(File(plugin.dataFolder, "lang"))
        for (langFile in languages) {
            val file = File(plugin.dataFolder, "lang" + File.separator + langFile)
            val lang = langFile.replace(".yml", "")
            val fileConfig = YamlConfiguration.loadConfiguration(file)
            val cfg = TranslationConfiguration.fromFileConfiguration(lang, fileConfig, fields, defaults)
            cfg.ifPresent { it.register(plugin) }
        }
    }

    fun sendMessage(sender: CommandSender, key: String, vararg args: Any?) {
        MessageFactory.get(plugin).sendMessage(sender, key, *args)
    }
}
