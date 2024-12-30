package net.guizhanss.fastmachines.core.recipes.raw

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import org.bukkit.inventory.ItemStack

data class RawRecipe(
    val inputs: List<RecipeChoice>,
    val output: List<ItemStack>,
)
