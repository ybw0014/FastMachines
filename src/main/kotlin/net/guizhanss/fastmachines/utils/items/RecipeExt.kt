package net.guizhanss.fastmachines.utils.items

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.choices.MultipleChoice
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import org.bukkit.inventory.RecipeChoice as BukkitRecipeChoice

fun Collection<RecipeChoice>.summarize(): Map<RecipeChoice, Int> {
    val map = mutableMapOf<RecipeChoice, Int>()

    for (choice in this) {
        map[choice] = map.getOrDefault(choice, 0) + 1
    }

    return map
}

fun BukkitRecipeChoice.asFMRecipeChoice() =
    when (this) {
        is BukkitRecipeChoice.MaterialChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first().toItem()))
            } else {
                MultipleChoice(choices.map { ItemWrapper.of(it.toItem()) })
            }
        }

        is BukkitRecipeChoice.ExactChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first()))
            } else {
                MultipleChoice(choices.map { ItemWrapper.of(it) })
            }
        }

        else -> {
            throw IllegalArgumentException("Unknown RecipeChoice type: $this")
        }
    }
