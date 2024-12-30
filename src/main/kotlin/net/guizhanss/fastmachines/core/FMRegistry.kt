package net.guizhanss.fastmachines.core

import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine
import java.util.LinkedList

internal object FMRegistry {

    val enabledFastMachines = LinkedList<AbstractFastMachine>()
}
