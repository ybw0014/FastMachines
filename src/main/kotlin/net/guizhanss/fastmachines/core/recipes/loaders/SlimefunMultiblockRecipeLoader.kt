package net.guizhanss.fastmachines.core.recipes.loaders

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine
import net.guizhanss.fastmachines.utils.items.summarize
import org.bukkit.inventory.ItemStack

/**
 * A [RecipeLoader] that loads recipes from a [MultiBlockMachine].
 */
class SlimefunMultiblockRecipeLoader(
    machine: AbstractFastMachine,
    private val id: String,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val mbm = SlimefunItem.getById(id)
        require(mbm is MultiBlockMachine) { "The item $id is not a multiblock machine." }

        val recipes = mbm.recipes
        require(recipes.size % 2 == 0) { "The multiblock machine $id has invalid recipe list." }

        for (i in recipes.indices step 2) {
            val input = recipes[i].filterNotNull().summarize().map { ExactChoice(it) }
            val output = recipes[i + 1].toList()

            rawRecipes.add(RawRecipe(input, output))
        }
    }

}
