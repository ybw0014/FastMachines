package net.guizhanss.fastmachines.core.recipes.loaders

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.RandomRecipe
import net.guizhanss.fastmachines.core.recipes.StandardRecipe
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine
import net.guizhanss.fastmachines.utils.items.isDisabled
import net.guizhanss.fastmachines.utils.items.summarize

/**
 * A [RecipeLoader] is responsible for loading recipes for a specific [AbstractFastMachine].
 *
 * Check the subclasses of this class for different implementations.
 */
abstract class RecipeLoader(
    val machine: AbstractFastMachine,
    val enableRandomRecipes: Boolean = false,
) {

    protected val rawRecipes: MutableList<RawRecipe> = mutableListOf()

    open fun beforeLoad() {
        // there is nothing here in default loader
    }

    open fun load() {
        beforeLoad()
        FastMachines.debug("Loading recipes for ${machine.id}...")
        if (enableRandomRecipes) {
            loadWithRandomRecipes()
        } else {
            loadRecipes()
        }
    }

    private fun loadWithRandomRecipes() {
        sortRecipes()

        val groupedRecipes = rawRecipes.groupBy { it.inputKey() }

        groupedRecipes.forEach { (inputKey, recipes) ->
            FastMachines.debug("===============")
            FastMachines.debug("Processing recipes with input key: $inputKey")

            val input = recipes.first().inputs.first()
            val outputs = recipes.map { it.output }.flatten().filter { !it.isDisabled() }

            // all disabled, no recipe
            if (outputs.isEmpty()) {
                return@forEach
            }

            FastMachines.debug("  - Input: $input")
            FastMachines.debug("  - Outputs: $outputs")

            val recipe = if (outputs.size > 1) {
                RandomRecipe(input, outputs)
            } else {
                StandardRecipe(mapOf(input to 1), outputs.first())
            }
            FastMachines.debug("  - Created recipe: $recipe")
            machine.addRecipe(recipe)
        }
    }

    private fun loadRecipes() {
        sortRecipes()

        rawRecipes.forEachIndexed { index, rawRecipe ->
            FastMachines.debug("===============")
            FastMachines.debug("Processing raw recipe (${index + 1}/${rawRecipes.size}): $rawRecipe")

            if (rawRecipe.output.size > 1) {
                FastMachines.debug("  - Unexpected multiple outputs, skipping")
                return@forEachIndexed
            }

            val outputItem = rawRecipe.output.first()

            // no need to load recipe if the output item is disabled
            if (outputItem.isDisabled()) {
                FastMachines.debug("  - Output item is a disabled Slimefun item, skipping")
                return@forEachIndexed
            }

            val input = rawRecipe.inputs.summarize()
            FastMachines.debug("  - Summarized input: $input")

            val recipe = StandardRecipe(input, outputItem)
            FastMachines.debug("  - Created recipe: $recipe")
            machine.addRecipe(recipe)
        }
    }

    private fun sortRecipes() {
        rawRecipes.sortWith(compareBy { it.inputKey() })
        FastMachines.debug("Sorted raw recipes:")
        rawRecipes.forEachIndexed { index, rawRecipe ->
            FastMachines.debug("  (${index + 1}/${rawRecipes.size}): $rawRecipe")
        }
    }

    private fun RawRecipe.inputKey(): String {
        val sortedInputChoices = inputs.map { choice ->
            choice.getChoices().sorted().joinToString(",") { it.toString() }
        }.sorted()
        return sortedInputChoices.joinToString("||")
    }
}
