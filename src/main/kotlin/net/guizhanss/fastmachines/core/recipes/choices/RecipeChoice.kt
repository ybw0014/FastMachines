package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import org.bukkit.inventory.ItemStack

/**
 * A recipe ingredient.
 */
interface RecipeChoice {

    fun getChoices(): List<ItemWrapper>

    fun isValid(item: ItemStack): Boolean
}
