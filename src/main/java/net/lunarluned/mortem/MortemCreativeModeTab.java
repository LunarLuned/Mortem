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

        // Food and Drinks Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addAfter(Items.COOKIE, ModItems.RESIN_CANDY);
            entries.addAfter(Items.SPIDER_EYE, ModItems.DRUG);
            entries.addBefore(Items.MUSHROOM_STEW, ModItems.CRIMSON_STEW, ModItems.WARPED_STEW, ModItems.FUNGAL_STEW, ModItems.SCRAMBLED_EGGS_TUSK, ModItems.MUSHROOM_STEW_TUSK);
            entries.addBefore(Items.CARROT, ModItems.HARD_BOILED_EGG, ModItems.SCRAMBLED_EGGS);
            entries.addAfter(Items.DRIED_KELP, ModItems.SUSHI_ROLL);
            entries.addAfter(Items.POTATO, ModItems.SWEET_POTATO);
            entries.addAfter(Items.PORKCHOP, ModItems.RAW_BACON);
            entries.addAfter(Items.COOKED_PORKCHOP, ModItems.COOKED_BACON);
            entries.addAfter(Items.BEEF, ModItems.BEEF_PATTY);
            entries.addAfter(Items.COOKED_BEEF, ModItems.COOKED_BEEF_PATTY);
            entries.addAfter(Items.BREAD, ModItems.TOASTED_BREAD, ModItems.TOAST, ModItems.BURGER, ModItems.BACON_BURGER);
        });

        // Ingredients Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.EGG, ModItems.EGG_BOWL, ModItems.EGG_TUSK);
            entries.addAfter(Items.BOWL, ModItems.HOGLIN_TUSK);
        });

    }

}
