package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

data class MultipleChoice(
    val items: List<ItemWrapper>
) : RecipeChoice {

    override fun getChoices() = items

    override fun isValid(item: ItemStack) = items.any { it.isSimilarTo(item) }
}
