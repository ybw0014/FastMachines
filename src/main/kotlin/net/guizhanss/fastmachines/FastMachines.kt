package net.guizhanss.fastmachines

import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.BlobBuildUpdater
import net.byteflux.libby.Library
import net.guizhanss.fastmachines.core.FMRegistry
import net.guizhanss.fastmachines.core.services.ConfigService
import net.guizhanss.fastmachines.core.services.IntegrationService
import net.guizhanss.fastmachines.core.services.LocalizationService
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups
import net.guizhanss.fastmachines.implementation.items.FMItems
import net.guizhanss.fastmachines.implementation.listeners.CauldronListener
import net.guizhanss.fastmachines.implementation.listeners.HopperListener
import net.guizhanss.fastmachines.implementation.listeners.SlimefunRegistryListener
import net.guizhanss.fastmachines.implementation.setup.ResearchSetup
import net.guizhanss.fastmachines.implementation.tasks.FastMachineTickingTask
import net.guizhanss.guizhanlib.libraries.BukkitLibraryManager
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.logging.Level

class FastMachines : AbstractAddon(
    GITHUB_USER, GITHUB_REPO, GITHUB_BRANCH, AUTO_UPDATE_KEY
) {

    override fun load() {
        // check if there is central repo prop defined
        val centralRepo = System.getProperty("centralRepository") ?: "https://repo1.maven.org/maven2/"

        logger.info("Loading libraries, please wait...")
        logger.info("If you stuck here for a long time, try to specify a mirror repository.")
        logger.info("Add -DcentralRepository=<url> to the JVM arguments.")

        // download libs
        val manager = BukkitLibraryManager(this)
        manager.addRepository(centralRepo)
        manager.loadLibrary(
            Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-stdlib").version("2.1.0").build()
        )
        manager.loadLibrary(
            Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-reflect").version("2.1.0").build()
        )

        logger.info("Loaded all required libraries.")
    }

    override fun enable() {
        instance = this

        FMRegistry

        // config
        configService = ConfigService(this)
        debug("Debug mode is enabled.")

        // localization
        log(Level.INFO, "Loading language...")
        val lang = configService.lang
        localization = LocalizationService(this, file)
        localization.idPrefix = "FM_"
        localization.addLanguage(lang)
        if (lang != DEFAULT_LANG) {
            localization.addLanguage(DEFAULT_LANG)
        }
        log(Level.INFO, "Loaded language {0}.", lang)

        // item groups setup
        FMItemGroups

        // item setup
        FMItems

        // researches setup
        if (configService.enableResearches) {
            ResearchSetup
        }

        // integrations
        integrationService = IntegrationService(this)

        // listeners & tasks
        setupListeners()
        setupTasks()

        // Metrics setup
        setupMetrics()
    }

    override fun disable() {
        Bukkit.getScheduler().cancelTasks(this)
    }

    override fun autoUpdate() {
        if (pluginVersion.startsWith("Dev")) {
            BlobBuildUpdater(this, file, githubRepo).start()
        } else if (pluginVersion.startsWith("Build")) {
            try {
                // use updater in lib plugin
                val clazz = Class.forName("net.guizhanss.minecraft.guizhanlib.updater.GuizhanUpdater")
                val updaterStart = clazz.getDeclaredMethod(
                    "start",
                    Plugin::class.java,
                    File::class.java,
                    String::class.java,
                    String::class.java,
                    String::class.java
                )
                updaterStart.invoke(null, this, file, githubUser, githubRepo, githubBranch)
            } catch (ignored: Exception) {
                // use updater in lib
                GuizhanBuildsUpdater.start(this, file, githubUser, githubRepo, githubBranch)
            }
        }
    }

    private fun setupListeners() {
        CauldronListener(this)
        HopperListener(this)
        SlimefunRegistryListener(this)
    }

    private fun setupTasks() {
        FastMachineTickingTask()
    }

    private fun setupMetrics() {
        val metrics = Metrics(this, 20046)
    }

    companion object {

        private const val GITHUB_USER = "ybw0014"
        private const val GITHUB_REPO = "FastMachines"
        private const val GITHUB_BRANCH = "master"
        private const val AUTO_UPDATE_KEY = "auto-update"
        const val DEFAULT_LANG = "en-US"

        lateinit var instance: FastMachines
            private set
        lateinit var configService: ConfigService
            private set
        lateinit var localization: LocalizationService
            private set
        lateinit var integrationService: IntegrationService
            private set

        fun scheduler() = getScheduler()

        fun log(level: Level, message: String) {
            instance.logger.log(level, message)
        }

        fun log(level: Level, ex: Throwable, message: String) {
            instance.logger.log(level, ex) { message }
        }

        fun debug(message: String) {
            if (!Companion::configService.isInitialized || !configService.debug) return
            log(Level.INFO, "[DEBUG] $message")
        }
    }
}
