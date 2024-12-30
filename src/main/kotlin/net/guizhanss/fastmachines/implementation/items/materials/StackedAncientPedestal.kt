package net.guizhanss.fastmachines.implementation.items.materials

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils
import net.guizhanss.fastmachines.utils.items.withAmount
import net.guizhanss.guizhanlib.minecraft.utils.InventoryUtil
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class StackedAncientPedestal(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : UnplaceableBlock(itemGroup, itemStack, recipeType, recipe) {

    @Nonnull
    override fun getItemHandler(): ItemUseHandler {
        return ItemUseHandler { e: PlayerRightClickEvent ->
            e.cancel()
            val p = e.player
            val pedestal = SlimefunItems.ANCIENT_PEDESTAL
            if (pedestal.item!!.isDisabledIn(p.world)) {
                return@ItemUseHandler
            }
            ItemUtils.consumeItem(e.item, true)
            InventoryUtil.push(p, pedestal.withAmount(4))
            SoundEffect.ANCIENT_ALTAR_START_SOUND.playFor(p)
        }
    }
}
