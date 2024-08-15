package net.guizhanss.fastmachines.core.services;

import javax.annotation.Nonnull;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.listeners.SlimefunRegistryLoadedListener;

public final class ListenerService {

    public ListenerService(@Nonnull FastMachines plugin) {
        new SlimefunRegistryLoadedListener(plugin);
    }
}
