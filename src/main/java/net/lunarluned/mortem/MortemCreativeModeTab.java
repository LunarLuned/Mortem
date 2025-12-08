package net.lunarluned.mortem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.IRON_ORE, ModBlocks.HARDENED_IRON_ORE, ModBlocks.HARDENED_DEEPSLATE_IRON_ORE);
        });

        // Redstone Blocks Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.RAIL, ModBlocks.COPPER_RAIL);
        });

        // Food and Drinks Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addAfter(Items.COOKIE, ModItems.RESIN_CANDY);
            // new foods
            entries.addAfter(Items.APPLE, ModItems.BAKED_APPLE);
            entries.addAfter(Items.CAKE, ModItems.RAW_PUMPKIN_GUTS, ModItems.BAKED_PUMPKIN_GUTS, ModItems.CAKE_SLICE, ModBlocks.APPLE_PIE, ModItems.APPLE_PIE_SLICE);

            entries.addAfter(Items.CHICKEN, ModItems.RAW_CHICKEN_NUGGET);
            entries.addAfter(Items.COOKED_CHICKEN, ModItems.COOKED_CHICKEN_NUGGET);

            entries.addAfter(Items.MUTTON, ModItems.RAW_MUTTON_SLICE);
            entries.addAfter(Items.COOKED_MUTTON, ModItems.COOKED_MUTTON_SLICE);

            entries.addAfter(Items.POTATO, ModItems.POTATO_WEDGE);
            entries.addAfter(Items.BAKED_POTATO, ModItems.BAKED_POTATO_WEDGE);


            entries.addAfter(Items.SPIDER_EYE, ModItems.DRUG);
            entries.addBefore(Items.MUSHROOM_STEW, ModItems.CRIMSON_STEW, ModItems.WARPED_STEW, ModItems.FUNGAL_STEW, ModItems.SCRAMBLED_EGGS_TUSK, ModItems.MUSHROOM_STEW_TUSK,
                    ModItems.SALAD, ModItems.CHICKEN_SALAD);
            entries.addBefore(Items.CARROT, ModItems.COOKED_SEEDS, ModItems.HARD_BOILED_EGG, ModItems.SCRAMBLED_EGGS);
            entries.addAfter(Items.DRIED_KELP, ModItems.SUSHI_ROLL);
            entries.addAfter(Items.BAKED_POTATO, ModItems.SWEET_POTATO);
            entries.addAfter(Items.PORKCHOP, ModItems.RAW_BACON);
            entries.addAfter(Items.COOKED_PORKCHOP, ModItems.COOKED_BACON, ModItems.BREAKFAST_SANDWICH);
            entries.addAfter(Items.BEEF, ModItems.BEEF_PATTY);
            entries.addAfter(Items.COOKED_BEEF, ModItems.COOKED_BEEF_PATTY);
            entries.addAfter(Items.BREAD, ModItems.TOASTED_BREAD, ModItems.TOAST, ModItems.BERRY_SPREAD_TOAST, ModItems.APPLE_SPREAD_TOAST, ModItems.CACTUS_SPREAD_TOAST, ModItems.COCOA_SPREAD_TOAST, ModItems.MUSHROOM_SPREAD_TOAST, ModItems.PORK_SPREAD_TOAST, ModItems.BURGER, ModItems.BACON_BURGER);
            entries.addAfter(Items.HONEY_BOTTLE, ModItems.SWEET_BERRY_JAM, ModItems.APPLE_JAM, ModItems.CACTUS_JAM, ModItems.COCOA_SPREAD, ModItems.MUSHROOM_PUREE, ModItems.PORK_PATE);
            entries.addAfter(ModItems.PORK_PATE,ModItems.POISON_ELIXIR, ModItems.ENHANCED_POISON_ELIXIR, ModItems.MISFORTUNE_ELIXIR, ModItems.ENHANCED_MISFORTUNE_ELIXIR, ModItems.VITALIZATION_ELIXIR, ModItems.ENHANCED_VITALIZATION_ELIXIR);
        });

        // Ingredients Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.EGG, ModItems.EGG_BOWL, ModItems.EGG_TUSK);
            entries.addAfter(Items.BOWL, ModItems.HOGLIN_TUSK);
        });

    }

}
