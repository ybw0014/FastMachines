package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

data class ExactChoice(
    val item: ItemWrapper
) : RecipeChoice {

    override fun getChoices() = listOf(item)

    override fun isValid(item: ItemStack) = this.item.isSimilarTo(item)
}
