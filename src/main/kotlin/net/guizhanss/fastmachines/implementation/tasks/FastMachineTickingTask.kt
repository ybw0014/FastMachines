package net.guizhanss.fastmachines.implementation.tasks

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.FMRegistry
import java.util.logging.Level

class FastMachineTickingTask {

    init {
        FMRegistry.enabledFastMachines.forEach { fm ->
            FastMachines.scheduler().repeatAsync(FastMachines.configService.fmTickRate) {
                for ((pos, cache) in fm.caches) {
                    try {
                        cache.tick()
                    } catch (ex: Exception) {
                        FastMachines.log(
                            Level.SEVERE,
                            ex,
                            "An error has occurred while ticking ${fm.javaClass.simpleName} at ${pos.world.name} ${pos.x} ${pos.y} ${pos.z}",
                        )
                    }
                }
            }
        }
    }
}
