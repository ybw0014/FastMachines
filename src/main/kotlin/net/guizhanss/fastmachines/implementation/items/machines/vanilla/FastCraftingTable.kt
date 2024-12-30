package net.guizhanss.fastmachines.implementation.items.machines.vanilla

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.loaders.MultipleLoader
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.VanillaRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.generic.BaseFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class FastCraftingTable(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BaseFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItem: ItemStack
        get() = FastMachines.localization.getItem("CRAFT", Material.CRAFTING_TABLE)

    override val recipeLoader: RecipeLoader
        get() = MultipleLoader(
            this,
            VanillaRecipeLoader(this, ShapedRecipe::class.java),
            VanillaRecipeLoader(this, ShapelessRecipe::class.java),
        )
}
