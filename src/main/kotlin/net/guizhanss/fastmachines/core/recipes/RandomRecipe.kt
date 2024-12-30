package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.utils.items.isDisabledIn
import org.bukkit.World
import org.bukkit.inventory.ItemStack

data class RandomRecipe(
    private val input: RecipeChoice,
    private val outputs: List<ItemStack>,
) : Recipe {

    override fun getInputs() = mapOf(input to 1)

    override fun getOutputs() = outputs

    override fun getOutput(world: World) = outputs.filter { !it.isDisabledIn(world) }.random()

    override fun isDisabledIn(world: World) = outputs.all { it.isDisabledIn(world) }
}
