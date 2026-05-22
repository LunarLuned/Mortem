package net.lunarluned.mortem;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.item.ModItemGroups;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class MortemCreativeModeTab {

    public static void registerCreativeTabs() {

        // Natural Blocks Tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.insertBefore(Blocks.IRON_ORE, ModBlocks.HARDENED_IRON_ORE, ModBlocks.HARDENED_DEEPSLATE_IRON_ORE);
        });

        // Redstone Blocks Tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.insertBefore(Blocks.RAIL, ModBlocks.COPPER_RAIL);
        });

        // Food and Drinks Tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.insertAfter(Items.COOKIE, ModItems.RESIN_CANDY);
            // new foods
            entries.insertAfter(Items.APPLE, ModItems.BAKED_APPLE);
            entries.insertAfter(Items.CAKE, ModItems.RAW_PUMPKIN_GUTS, ModItems.BAKED_PUMPKIN_GUTS, ModBlocks.PUMPKIN_PIE_BLOCK, ModItems.PUMPKIN_PIE_SLICE, ModItems.CAKE_SLICE, ModBlocks.APPLE_PIE, ModItems.APPLE_PIE_SLICE);

            entries.insertAfter(Items.CHICKEN, ModItems.RAW_CHICKEN_NUGGET);
            entries.insertAfter(Items.COOKED_CHICKEN, ModItems.COOKED_CHICKEN_NUGGET);

            entries.insertAfter(Items.MUTTON, ModItems.RAW_MUTTON_SLICE);
            entries.insertAfter(Items.COOKED_MUTTON, ModItems.COOKED_MUTTON_SLICE);

            entries.insertAfter(Items.POTATO, ModItems.POTATO_WEDGE);
            entries.insertAfter(Items.BAKED_POTATO, ModItems.BAKED_POTATO_WEDGE);


            entries.insertAfter(Items.SPIDER_EYE, ModItems.DRUG);
            entries.insertBefore(Items.MUSHROOM_STEW, ModItems.CRIMSON_STEW, ModItems.WARPED_STEW, ModItems.FUNGAL_STEW, ModItems.SCRAMBLED_EGGS_TUSK, ModItems.MUSHROOM_STEW_TUSK,
                    ModItems.SALAD, ModItems.CHICKEN_SALAD);
            entries.insertBefore(Items.CARROT, ModItems.COOKED_SEEDS, ModItems.HARD_BOILED_EGG, ModItems.SCRAMBLED_EGGS);
            entries.insertAfter(Items.DRIED_KELP, ModItems.SUSHI_ROLL);
            entries.insertAfter(Items.BAKED_POTATO, ModItems.SWEET_POTATO);
            entries.insertAfter(Items.PORKCHOP, ModItems.RAW_BACON);
            entries.insertAfter(Items.COOKED_PORKCHOP, ModItems.COOKED_BACON, ModItems.BREAKFAST_SANDWICH);
            entries.insertAfter(Items.BEEF, ModItems.BEEF_PATTY);
            entries.insertAfter(Items.COOKED_BEEF, ModItems.COOKED_BEEF_PATTY);
            entries.insertAfter(Items.BREAD, ModItems.TOASTED_BREAD, ModItems.TOAST, ModItems.BERRY_SPREAD_TOAST, ModItems.APPLE_SPREAD_TOAST, ModItems.CACTUS_SPREAD_TOAST, ModItems.COCOA_SPREAD_TOAST, ModItems.MUSHROOM_SPREAD_TOAST, ModItems.PORK_SPREAD_TOAST, ModItems.BURGER, ModItems.BACON_BURGER);
            entries.insertAfter(Items.HONEY_BOTTLE, ModItems.SWEET_BERRY_JAM, ModItems.APPLE_JAM, ModItems.CACTUS_JAM, ModItems.COCOA_SPREAD, ModItems.MUSHROOM_PUREE, ModItems.PORK_PATE);
            entries.insertAfter(ModItems.PORK_PATE,ModItems.POISON_ELIXIR, ModItems.ENHANCED_POISON_ELIXIR, ModItems.MISFORTUNE_ELIXIR, ModItems.ENHANCED_MISFORTUNE_ELIXIR, ModItems.VITALIZATION_ELIXIR, ModItems.ENHANCED_VITALIZATION_ELIXIR);
        });

        // Ingredients Tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.insertAfter(Items.EGG, ModItems.EGG_BOWL, ModItems.EGG_TUSK);
            entries.insertAfter(Items.BOWL, ModItems.HOGLIN_TUSK);
        });

    }

}
