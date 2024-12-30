package net.guizhanss.fastmachines.implementation.items

import io.github.seggan.sf4k.item.builder.ItemRegistry
import io.github.seggan.sf4k.item.builder.asMaterialType
import io.github.seggan.sf4k.item.builder.buildRecipe
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastEnhancedCraftingTable
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreWasher
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastCraftingTable
import net.guizhanss.fastmachines.implementation.items.materials.StackedAncientPedestal
import net.guizhanss.fastmachines.utils.items.builder.buildSlimefunItem
import net.guizhanss.fastmachines.utils.items.toItem
import org.bukkit.Material

object FMItems : ItemRegistry(FastMachines.instance, FastMachines.localization.idPrefix) {

    //<editor-fold desc="Materials" collapsed="true">
    val ETERNAL_FIRE by buildSlimefunItem<UnplaceableBlock> {
        material = Material.IRON_INGOT.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +"FFF"
            +"CNC"
            'F' means Material.FLINT_AND_STEEL
            'C' means SlimefunItems.IGNITION_CHAMBER
            'N' means Material.NETHERRACK
        }
    }

    val FAST_CORE by buildSlimefunItem<UnplaceableBlock> {
        material = Material.CONDUIT.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" C "
            +" R "
            'C' means SlimefunItems.SMALL_CAPACITOR
            'R' means SlimefunItems.REDSTONE_ALLOY
        }
    }

    val STACKED_ANCIENT_PEDESTAL by buildSlimefunItem<StackedAncientPedestal> {
        material = Material.DISPENSER.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"A A"
            +"   "
            +"A A"
            'A' means SlimefunItems.ANCIENT_PEDESTAL
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Vanilla" collapsed="true">
    val FAST_CRAFTING_TABLE by buildSlimefunItem<FastCraftingTable> {
        material = Material.CRAFTING_TABLE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" c "
            +" C "
            +" o "
            'c' means Material.CHEST
            'C' means Material.CRAFTING_TABLE
            'o' means if (FastMachines.configService.fmUseEnergy) FAST_CORE else null
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Slimefun" collapsed="true">
//    val FAST_ENHANCED_CRAFTING_TABLE by buildSlimefunItem<FastEnhancedCraftingTable> {
//        material = Material.CARTOGRAPHY_TABLE.asMaterialType()
//        itemGroup = FMItemGroups.MACHINES
//        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
//        recipe = buildRecipe {
//            +"   "
//            +" C "
//            +"ODo"
//            'C' means Material.CRAFTING_TABLE
//            'O' means SlimefunItems.OUTPUT_CHEST
//            'D' means Material.DISPENSER
//            'o' means if (FastMachines.configService.fmUseEnergy) FAST_CORE else null
//        }
//    }
//
//    val FAST_ORE_WASHER by buildSlimefunItem<FastOreWasher> {
//        material = Material.CAULDRON.asMaterialType()
//        itemGroup = FMItemGroups.MACHINES
//        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
//        recipe = buildRecipe {
//            +" G "
//            +" F "
//            +"ODo"
//            'G' means Material.GLASS
//            'F' means Material.OAK_FENCE
//            'O' means SlimefunItems.OUTPUT_CHEST
//            'D' means Material.DISPENSER.toItem()
//            'o' means if (FastMachines.configService.fmUseEnergy) FAST_CORE else null
//        }
//    }
    //</editor-fold>
}
