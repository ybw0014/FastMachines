package net.guizhanss.fastmachines.core.recipes.loaders

import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine

/**
 * A [RecipeLoader] that loads recipes from multiple [RecipeLoader]s.
 */
class MultipleLoader(
    machine: AbstractFastMachine,
    vararg loaders: RecipeLoader,
) : RecipeLoader(machine) {

    private val loaders = loaders.toList()

    override fun load() {
        for (loader in loaders) {
            loader.load()
        }
    }
}
