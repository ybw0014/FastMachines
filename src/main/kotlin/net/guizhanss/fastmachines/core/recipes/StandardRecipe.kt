package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.utils.items.isDisabledIn
import org.bukkit.World
import org.bukkit.inventory.ItemStack

data class StandardRecipe(
    private val inputs: Map<RecipeChoice, Int>,
    private val output: ItemStack,
) : Recipe {

    override fun getInputs() = inputs

    override fun getOutputs() = listOf(output)

    override fun getOutput(world: World) = output

    override fun isDisabledIn(world: World) = output.isDisabledIn(world)
}
