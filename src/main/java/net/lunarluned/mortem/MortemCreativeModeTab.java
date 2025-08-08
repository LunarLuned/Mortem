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
            entries.addAfter(Blocks.STONE, ModBlocks.CRUMBLING_STONE);
            entries.addBefore(Blocks.IRON_ORE, ModBlocks.HARDENED_IRON_ORE);
            entries.addAfter(Blocks.NETHERRACK, ModBlocks.CRUMBLING_NETHERRACK);
            entries.addAfter(Blocks.DEEPSLATE, ModBlocks.CRUMBLING_DEEPSLATE);
            entries.addAfter(Blocks.END_STONE, ModBlocks.CRUMBLING_END_STONE);
        });

        // Building Blocks Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.INFESTED_STONE, ModBlocks.CRUMBLING_STONE, ModBlocks.CRUMBLING_NETHERRACK, ModBlocks.CRUMBLING_DEEPSLATE, ModBlocks.CRUMBLING_END_STONE);
        });

        // Combat Tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addBefore(Items.HONEY_BOTTLE, ModItems.HEALING_VIAL);
        });
    }

}
