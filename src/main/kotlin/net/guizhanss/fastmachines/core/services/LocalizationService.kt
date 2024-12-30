package net.guizhanss.fastmachines.core.services

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.utils.listYmlFilesInJar
import net.guizhanss.fastmachines.utils.toId
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import net.guizhanss.guizhanlib.slimefun.addon.SlimefunLocalization
import org.bukkit.command.CommandSender
import java.io.File
import java.text.MessageFormat

class LocalizationService(
    private val plugin: FastMachines,
    private val jarFile: File
) : SlimefunLocalization(plugin) {

    init {
        extractTranslations()
    }

    private fun extractTranslations() {
        val translationsFolder = File(plugin.dataFolder, FOLDER_NAME)
        if (!translationsFolder.exists()) {
            translationsFolder.mkdirs()
        }
        val translationFiles = listYmlFilesInJar(jarFile, FOLDER_NAME)
        for (translationFile in translationFiles) {
            val filePath = FOLDER_NAME + File.separator + translationFile
            plugin.saveResource(filePath, true)
        }
    }

    fun getString(key: String, vararg args: Any?): String = MessageFormat.format(getString(key), *args)

    // items
    fun getItemName(itemId: String, vararg args: Any?) = getString("items.${itemId.toId()}.name", *args)
    fun getItemLore(itemId: String): List<String> = getStringList("items.${itemId.toId()}.lore")

    fun sendMessage(sender: CommandSender, key: String, vararg args: Any) {
        if (FastMachines.integrationService.slimefunTranslationEnabled) {
            FastMachines.integrationService.sendMessage(sender, key, *args)
        } else {
            ChatUtil.send(sender, MessageFormat.format(getString("messages.$key"), *args))
        }
    }

    companion object {

        const val FOLDER_NAME = "lang"
    }
}
