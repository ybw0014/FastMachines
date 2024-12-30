package net.guizhanss.fastmachines.core.items

import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

/**
 * A wrapper for [ItemStack] that only differs by the amount.
 */
@ConsistentCopyVisibility
data class ItemWrapper private constructor(
    val baseItem: ItemStack,
    val amount: Int,
) : Comparable<ItemWrapper> {

    // cache ItemMeta and hashCode
    val baseItemMeta = if (baseItem.hasItemMeta()) baseItem.itemMeta else null
    private val itemHash = baseItem.hashCode()

    /**
     * Returns a copy of this [ItemWrapper] with the given amount.
     */
    fun withAmount(amount: Int) = copy(amount = amount)

    /**
     * Returns a copy of this [ItemWrapper] with the amount increased by the given amount.
     */
    fun withExtraAmount(amount: Int) = withAmount(this.amount + amount)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemWrapper

        if (amount != other.amount) return false
        if (!isSimilarTo(other.baseItem)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = amount
        result = 31 * result + itemHash
        return result
    }

    override fun compareTo(other: ItemWrapper): Int {
        // quick compare hash code, items with different hash code are likely different
        if (itemHash != other.itemHash) return itemHash - other.itemHash

        // now same hash, compare amount
        if (amount != other.amount) return amount - other.amount

        // same hash and amount, compare item
        if (!isSimilarTo(other.baseItem)) return baseItem.hashCode() - other.baseItem.hashCode()

        // should be same
        return 0
    }

    companion object {

        /**
         * Creates a new [ItemWrapper] with the given [ItemStack] and amount.
         */
        fun of(item: ItemStack, amount: Int = item.amount): ItemWrapper {
            val baseItem = item.clone().apply { setAmount(1) }

            return ItemWrapper(baseItem, amount)
        }
    }
}
