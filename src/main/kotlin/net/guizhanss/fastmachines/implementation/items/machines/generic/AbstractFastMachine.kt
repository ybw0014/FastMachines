package net.guizhanss.fastmachines.implementation.items.machines.generic

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.FMRegistry
import net.guizhanss.fastmachines.core.recipes.Recipe
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.utils.constants.HeadTexture
import net.guizhanss.fastmachines.utils.items.withLore
import net.guizhanss.fastmachines.utils.items.withName
import net.guizhanss.guizhanlib.slimefun.machines.MenuBlock
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture as SlimefunHeadTexture

/**
 * The base fast machine.
 */
abstract class AbstractFastMachine(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    energyCapacity: Int,
    energyConsumption: Int,
) : MenuBlock(itemGroup, itemStack, recipeType, recipe), EnergyNetComponent {

    val recipes: List<Recipe>
        get() = _recipes
    val caches: Map<BlockPosition, FastMachineCache>
        get() = _caches

    private val _recipes = mutableListOf<Recipe>()
    private val _caches = mutableMapOf<BlockPosition, FastMachineCache>()
    private var recipeLocked = false

    private val energyCapacitySetting = IntRangeSetting(this, "energy-capacity", 0, energyCapacity, Int.MAX_VALUE)
    private val energyConsumptionSetting = IntRangeSetting(this, "energy-per-use", 0, energyConsumption, Int.MAX_VALUE)

    init {
        addItemSetting(energyCapacitySetting, energyConsumptionSetting)
    }

    fun addRecipe(recipe: Recipe) {
        check(!recipeLocked) { "Cannot add recipes after recipes are locked" }
        _recipes.add(recipe)
    }

    internal fun lockRecipes() {
        check(!recipeLocked) { "Recipes are already locked" }
        recipeLocked = true
    }

    override fun getEnergyComponentType() = EnergyNetComponentType.CONSUMER

    fun getEnergyPerUse() = if (FastMachines.configService.fmUseEnergy) energyConsumptionSetting.value else 0

    override fun getCapacity() = if (FastMachines.configService.fmUseEnergy) energyCapacitySetting.value else 0

    override fun setup(preset: BlockMenuPreset) {
        for (slot in PREVIEW_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
        }
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler())
        preset.addItem(CHOICE_SLOT, NO_ITEM, ChestMenuUtils.getEmptyClickHandler())
        preset.addItem(SCROLL_UP_SLOT, SCROLL_UP_ITEM, ChestMenuUtils.getEmptyClickHandler())
        preset.addItem(SCROLL_DOWN_SLOT, SCROLL_DOWN_ITEM, ChestMenuUtils.getEmptyClickHandler())

        preset.addItem(CRAFT_SLOT, craftItem, ChestMenuUtils.getEmptyClickHandler())
        preset.addItem(
            ENERGY_SLOT,
            SlimefunHeadTexture.ENERGY_CONNECTOR.asItemStack
                .withName(" ")
                .withLore(LoreBuilder.power(getEnergyPerUse(), FastMachines.localization.getString("lores.per-craft"))),
            ChestMenuUtils.getEmptyClickHandler()
        )
    }

    override fun getInputSlots() = INPUT_SLOTS

    override fun getOutputSlots() = intArrayOf()

    abstract val craftItem: ItemStack

    abstract val recipeLoader: RecipeLoader

    override fun onNewInstance(menu: BlockMenu, b: Block) {
        val pos = BlockPosition(b)
        _caches.put(pos, FastMachineCache(this, menu))
    }

    override fun onBreak(e: BlockBreakEvent, menu: BlockMenu) {
        super.onBreak(e, menu)
        val loc = menu.location
        menu.dropItems(loc, *INPUT_SLOTS)
        _caches.remove(BlockPosition(loc))
    }

    override fun postRegister() {
        super.postRegister()
        if (!isDisabled) {
            FMRegistry.enabledFastMachines.add(this)
        }
    }

    companion object {

        internal val INPUT_SLOTS = intArrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
        )
        internal val OUTPUT_SLOTS = intArrayOf(
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            0, 1, 2, 3, 4, 5, 6, 7, 8,
        )
        internal val PREVIEW_SLOTS = intArrayOf(
            36, 37, 38, 39, 40, 41,
            45, 46, 47, 48, 49, 50,
        )
        internal const val SCROLL_UP_SLOT = 42
        internal const val SCROLL_DOWN_SLOT = 51
        internal const val CHOICE_SLOT = 52
        internal const val CRAFT_SLOT = 53
        internal const val INFO_SLOT = 43
        internal const val ENERGY_SLOT = 44

        internal val ITEMS_PER_PAGE = PREVIEW_SLOTS.size

        internal val NO_ITEM = FastMachines.localization.getItem("NO_ITEM", Material.BARRIER)
        internal val SCROLL_UP_ITEM = FastMachines.localization.getItem("SCROLL_UP", HeadTexture.ARROW_UP.texture)
        internal val SCROLL_DOWN_ITEM = FastMachines.localization.getItem("SCROLL_DOWN", HeadTexture.ARROW_DOWN.texture)
        internal val INFO_ITEM = FastMachines.localization.getItem("INFO", HeadTexture.INFO.texture)
    }

}
