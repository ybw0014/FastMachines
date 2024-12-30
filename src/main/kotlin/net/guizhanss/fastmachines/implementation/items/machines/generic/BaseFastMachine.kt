package net.guizhanss.fastmachines.implementation.items.machines.generic

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.inventory.ItemStack

/**
 * The basic machine level fast machine.
 */
abstract class BaseFastMachine(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : AbstractFastMachine(itemGroup, itemStack, recipeType, recipe, 1024, 8) {
}
