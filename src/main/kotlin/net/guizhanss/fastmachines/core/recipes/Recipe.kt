package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import org.bukkit.World
import org.bukkit.inventory.ItemStack

/**
 * A shapeless recipe.
 */
interface Recipe {

    fun getInputs(): Map<RecipeChoice, Int>

    fun getOutputs(): List<ItemStack>

    fun getOutput(world: World): ItemStack

    fun isDisabledIn(world: World): Boolean
}
