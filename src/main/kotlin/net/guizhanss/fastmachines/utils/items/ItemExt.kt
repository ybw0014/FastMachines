@file:Suppress("deprecation")

package net.guizhanss.fastmachines.utils.items

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import net.guizhanss.guizhanlib.minecraft.utils.MinecraftVersionUtil
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.AxolotlBucketMeta
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.inventory.meta.BundleMeta
import org.bukkit.inventory.meta.CompassMeta
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.FireworkEffectMeta
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.SuspiciousStewMeta
import org.bukkit.inventory.meta.TropicalFishBucketMeta

/**
 * Get a new [ItemStack] with the given [Material] and amount.
 */
fun Material.toItem(amount: Int = 1) = ItemStack(this, amount)

/**
 * Get a copy of this [ItemStack] with the given amount.
 *
 * There is no check for the stack size limit.
 */
fun ItemStack.withAmount(amount: Int) =
    clone().apply { setAmount(amount) }

/**
 * Get a copy of this [ItemStack] with the given display name.
 */
fun ItemStack.withName(name: String) =
    clone().apply {
        itemMeta = itemMeta.apply { setDisplayName(ChatUtil.color(name)) }
    }

/**
 * Get a copy of this [ItemStack] with the given lore.
 */
fun ItemStack.withLore(lore: List<String>) =
    clone().apply {
        itemMeta = itemMeta.apply { setLore(lore.map { ChatUtil.color(it) }) }
    }

@JvmName("withLoreVararg")
fun ItemStack.withLore(vararg lore: String) = withLore(lore.toList())

/**
 * Strictly compares a [ItemWrapper] to an [ItemStack].
 *
 * Modified from [Networks by Sefiraat](https://github.com/Sefiraat/Networks/blob/master/src/main/java/io/github/sefiraat/networks/utils/StackUtils.java).
 */
fun ItemWrapper?.isSimilarTo(other: ItemStack?, checkAmount: Boolean = false, checkLore: Boolean = false): Boolean {
    // null check
    if (this == null || other == null) return false

    // type check
    if (baseItem.type != other.type) return false
    if (baseItem.type.isAir || other.type.isAir) return false

    // optional amount check
    if (checkAmount && amount != other.amount) return false

    // meta check
    if (!baseItem.hasItemMeta() || !other.hasItemMeta()) {
        return baseItem.hasItemMeta() == other.hasItemMeta()
    }

    val thisMeta = baseItemMeta!!
    val otherMeta = other.itemMeta

    if (thisMeta.javaClass != otherMeta.javaClass) return false

    if (thisMeta.quickNotEquals(otherMeta)) return false

    // display name check
    if (thisMeta.hasDisplayName() != otherMeta.hasDisplayName()) return false

    // custom model data check
    if (!thisMeta.hasCustomModelData() || !otherMeta.hasCustomModelData()) {
        if (thisMeta.hasCustomModelData() != otherMeta.hasCustomModelData()) return false
    } else {
        if (thisMeta.customModelData != otherMeta.customModelData) return false
    }

    // pdc check
    if (thisMeta.persistentDataContainer != otherMeta.persistentDataContainer) return false

    // enchantments check
    if (thisMeta.enchants != otherMeta.enchants) return false

    // item flags check
    if (thisMeta.itemFlags != otherMeta.itemFlags) return false

    // lore check
    if (checkLore && thisMeta.lore != otherMeta.lore) return false

    // sf id check (distinction is covered with pdc and lore)
    if (Slimefun.instance() != null) {
        val sfIdThis = Slimefun.getItemDataService().getItemData(thisMeta)
        val sfIdOther = Slimefun.getItemDataService().getItemData(otherMeta)
        if (sfIdThis.isPresent && sfIdOther.isPresent) {
            return sfIdThis.get() == sfIdOther.get()
        }
    }

    // display name check
    if (thisMeta.hasDisplayName() && (thisMeta.displayName != otherMeta.displayName)) {
        return false
    }

    return true
}

private fun ItemMeta.quickNotEquals(other: ItemMeta): Boolean {
    if (this is Damageable && other is Damageable) {
        if (this.damage != other.damage) return true
    }

    if (this is AxolotlBucketMeta && other is AxolotlBucketMeta) {
        if (this.hasVariant() != other.hasVariant()) return true
        if (!this.hasVariant() || !other.hasVariant()) return true
        if (this.variant != other.variant) return true
    }

    if (this is BannerMeta && other is BannerMeta) {
        if (this.patterns != other.patterns) return true
    }

    if (this is BookMeta && other is BookMeta) {
        if (this.pageCount != other.pageCount) return true
        if (this.author != other.author) return true
        if (this.title != other.title) return true
        if (this.generation != other.generation) return true
    }

    if (this is BundleMeta && other is BundleMeta) {
        if (this.items != other.items) return true
    }

    if (this is CompassMeta && other is CompassMeta) {
        if (this.isLodestoneTracked != other.isLodestoneTracked) return true
        if (this.lodestone != other.lodestone) return true
    }

    if (this is EnchantmentStorageMeta && other is EnchantmentStorageMeta) {
        if (this.hasStoredEnchants() != other.hasStoredEnchants()) return true
        if (this.storedEnchants != other.storedEnchants) return true
    }

    if (this is FireworkEffectMeta && other is FireworkEffectMeta) {
        if (this.effect != other.effect) return true
    }

    if (this is FireworkMeta && other is FireworkMeta) {
        if (this.power != other.power) return true
        if (this.effects != other.effects) return true
    }

    if (this is LeatherArmorMeta && other is LeatherArmorMeta) {
        if (this.color != other.color) return true
    }

    if (this is MapMeta && other is MapMeta) {
        if (this.hasMapView() != other.hasMapView()) return true
        if (this.hasLocationName() != other.hasLocationName()) return true
        if (this.hasColor() != other.hasColor()) return true
        if (this.mapView != other.mapView) return true
        if (this.locationName != other.locationName) return true
        if (this.color != other.color) return true
    }

    if (this is PotionMeta && other is PotionMeta) {
        if (MinecraftVersionUtil.isAtLeast(20, 5)) {
            if (this.basePotionType != other.basePotionType) return true
        } else {
            if (this.basePotionData != other.basePotionData) return true
        }
        if (this.hasCustomEffects() != other.hasCustomEffects()) return true
        if (this.hasColor() != other.hasColor()) return true
        if (this.color != other.color) return true
        if (this.customEffects != other.customEffects) return true
    }

    if (this is SkullMeta && other is SkullMeta) {
        if (this.hasOwner() != other.hasOwner()) return true
        if (this.owningPlayer != other.owningPlayer) return true
    }

    if (this is SuspiciousStewMeta && other is SuspiciousStewMeta) {
        if (this.customEffects != other.customEffects) return true
    }

    if (this is TropicalFishBucketMeta && other is TropicalFishBucketMeta) {
        if (this.hasVariant() != other.hasVariant()) return true
        if (this.pattern != other.pattern) return true
        if (this.bodyColor != other.bodyColor) return true
        if (this.patternColor != other.patternColor) return true
    }

    return false
}

/**
 * Returns a list of [ItemWrapper]s that all similar items are merged into one wrapper.
 */
fun Collection<ItemStack?>.summarize(): List<ItemWrapper> {
    val result = mutableListOf<ItemWrapper>()

    for (item in this) {
        if (item == null || item.type.isAir) continue

        val existingIdx = result.indexOfFirst { it.isSimilarTo(item) }
        if (existingIdx != -1) {
            result[existingIdx] = result[existingIdx].withExtraAmount(item.amount)
        } else {
            result.add(ItemWrapper.of(item))
        }
    }

    return result.sorted()
}

