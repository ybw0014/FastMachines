@file:Suppress("UNCHECKED_CAST")

package net.guizhanss.fastmachines.core.recipes.loaders

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine
import net.guizhanss.fastmachines.utils.items.asFMRecipeChoice
import net.guizhanss.fastmachines.utils.reflections.resultItem
import org.bukkit.Bukkit
import org.bukkit.inventory.CookingRecipe
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

/**
 * A [RecipeLoader] that loads recipes from vanilla registry.
 */
class VanillaRecipeLoader<T : Recipe>(
    machine: AbstractFastMachine,
    private val recipeClass: Class<T>,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val iterator = Bukkit.getServer().recipeIterator()
        while (iterator.hasNext()) {
            val recipe = iterator.next()
            if (recipeClass.isInstance(recipe)) {
                registerRecipe(recipe as T)
            }
        }
    }

    private fun registerRecipe(recipe: T) {
        when (recipe) {
            is ShapedRecipe -> {
                val ingredients = mutableListOf<RecipeChoice>()
                val shape = recipe.shape.joinToString("").filter { !it.isWhitespace() }.toCharArray()

                for (c in shape) {
                    val choice = recipe.choiceMap[c] ?: continue
                    ingredients.add(choice.asFMRecipeChoice())
                }

                rawRecipes.add(RawRecipe(ingredients, listOf(recipe.resultItem)))
            }

            is ShapelessRecipe -> {
                rawRecipes.add(RawRecipe(recipe.choiceList.map { it.asFMRecipeChoice() }, listOf(recipe.resultItem)))
            }

            is CookingRecipe<*> -> {
                rawRecipes.add(RawRecipe(listOf(recipe.inputChoice.asFMRecipeChoice()), listOf(recipe.result)))
            }
        }
    }

}
