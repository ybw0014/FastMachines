package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.attributes.NotACauldron
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.generic.BaseFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastOreWasher(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BaseFastMachine(itemGroup, itemStack, recipeType, recipe), NotACauldron {

    override val craftItem: ItemStack
        get() = FastMachines.localization.getItem("CRAFT", Material.CAULDRON)

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SlimefunItems.ORE_WASHER.itemId, true)
}
