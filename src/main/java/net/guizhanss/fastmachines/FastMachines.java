package net.guizhanss.fastmachines;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.guizhanss.guizhanlib.minecraft.utils.MinecraftVersionUtil;

import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.BlobBuildUpdater;

import net.guizhanss.fastmachines.core.Registry;
import net.guizhanss.fastmachines.core.services.ConfigurationService;
import net.guizhanss.fastmachines.core.services.IntegrationService;
import net.guizhanss.fastmachines.core.services.ListenerService;
import net.guizhanss.fastmachines.core.services.LocalizationService;
import net.guizhanss.fastmachines.setup.Items;
import net.guizhanss.fastmachines.setup.Researches;
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon;
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater;

import org.bstats.bukkit.Metrics;

public final class FastMachines extends AbstractAddon {

    public static final String DEFAULT_LANG = "en-US";

    private Registry registry;
    private ConfigurationService configService;
    private LocalizationService localization;
    private IntegrationService integrationService;
    private boolean debugEnabled = false;

    public FastMachines() {
        super("ybw0014", "FastMachines", "master", "auto-update");
    }

    @Nonnull
    public static Registry getRegistry() {
        return inst().registry;
    }

    @Nonnull
    public static ConfigurationService getConfigService() {
        return inst().configService;
    }

    @Nonnull
    public static LocalizationService getLocalization() {
        return inst().localization;
    }

    @Nonnull
    public static IntegrationService getIntegrationService() {
        return inst().integrationService;
    }

    public static void debug(@Nonnull String message, @Nonnull Object... args) {
        Preconditions.checkNotNull(message, "message cannot be null");

        if (inst().debugEnabled) {
            inst().getLogger().log(Level.INFO, "[DEBUG] " + message, args);
        }
    }

    @Nonnull
    private static FastMachines inst() {
        return getInstance();
    }

    @Override
    protected void load() {
        // check sf version
        if (!checkSlimefunVersion()) return;
    }

    @Override
    public void enable() {
        if (MinecraftVersionUtil.isAtLeast(21)) {
            var sfVersion = getServer().getPluginManager().getPlugin("Slimefun").getDescription().getVersion().toLowerCase();
            if (sfVersion.startsWith("rc") && !sfVersion.startsWith("dev")) {
                getLogger().severe("FastMachines is not compatible with current Slimefun version on Minecraft 1.21 and above.");
                getLogger().severe("We are rewriting the recipe system to handle 1.21 changes,");
                getLogger().severe("subscribe to the Addon Community announcement channel to get the latest news.");
                return;
            }
        }

        log(Level.INFO, "====================");
        log(Level.INFO, "     FastMachines   ");
        log(Level.INFO, "     by ybw0014     ");
        log(Level.INFO, "====================");

        // registry
        registry = new Registry();

        // config
        configService = new ConfigurationService(this);

        // debug
        debugEnabled = configService.isDebugEnabled();

        // localization
        log(Level.INFO, "Loading language...");
        String lang = configService.getLang();
        localization = new LocalizationService(this, getFile());
        try {
            localization.addLanguage(lang);
        } catch (Exception e) {
            log(Level.SEVERE, "An error has occurred while loading language " + lang);
        }
        if (!lang.equals(DEFAULT_LANG)) {
            localization.addLanguage(DEFAULT_LANG);
        }
        localization.setIdPrefix("FM_");
        log(Level.INFO, localization.getString("console.loaded-language"), lang);

        // items
        log(Level.INFO, localization.getString("console.loading-items"));
        Items.setup(this);

        // integrations
        integrationService = new IntegrationService(this);

        // researches
        if (configService.isEnableResearches()) {
            log(Level.INFO, localization.getString("console.loading-researches"));
            Researches.setup();
            Researches.register();
        }

        new ListenerService(this);

        setupMetrics();
    }

    @Override
    public void disable() {
        // nothing to do here for now
    }

    private void setupMetrics() {
        new Metrics(this, 20046);
    }

    @Override
    protected void autoUpdate() {
        if (getPluginVersion().startsWith("Dev")) {
            new BlobBuildUpdater(this, getFile(), getGithubRepo()).start();
        } else if (getPluginVersion().startsWith("Build")) {
            try {
                // use updater in lib plugin

                // this little trick because maven shade plugin will change strings
                char[] pluginPackage = {
                    'n', 'e', 't', '.', 'g', 'u', 'i', 'z', 'h', 'a', 'n', 's', 's', '.',
                    'g', 'u', 'i', 'z', 'h', 'a', 'n', 'l', 'i', 'b', 'p', 'l', 'u', 'g', 'i', 'n'
                };
                Class<?> clazz = Class.forName(new String(pluginPackage) + ".updater.GuizhanUpdater");
                Method updaterStart = clazz.getDeclaredMethod("start", Plugin.class, File.class, String.class, String.class, String.class);
                updaterStart.invoke(null, this, getFile(), getGithubUser(), getGithubRepo(), getGithubBranch());
            } catch (Exception ignored) {
                // use updater in lib
                GuizhanBuildsUpdater.start(this, getFile(), getGithubUser(), getGithubRepo(), getGithubBranch());
            }
        }
    }

    private boolean checkSlimefunVersion() {
        String sfVersion = getServer().getPluginManager().getPlugin("Slimefun").getDescription().getVersion();
        if (sfVersion.startsWith("DEV - 1104")) {
            for (int i = 0; i < 100; i++) {
                getLogger().severe("You are using a damn ancient version of Slimefun, update Slimefun first!");
                getLogger().severe("Download Slimefun from here: https://blob.build/project/Slimefun4/Dev");
                getLogger().severe("Also join the Slimefun discord so you can get the announcements for new versions and will not be a primitive any more.");
                getLogger().severe("https://discord.gg/slimefun");
            }
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}
