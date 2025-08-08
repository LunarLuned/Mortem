package net.lunarluned.mortem;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.lunarluned.mortem.MortemCreativeModeTab.registerCreativeTabs;
import static net.lunarluned.mortem.block.ModBlocks.registerModBlocks;
import static net.lunarluned.mortem.effect.ModEffects.registerEffects;
import static net.lunarluned.mortem.item.ModItemGroups.registerItemGroups;
import static net.lunarluned.mortem.item.ModItems.registerModItems;

public class Mortem implements ModInitializer {
	public static final String MOD_ID = "mortem";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		registerModBlocks();
		registerModItems();
		registerItemGroups();
		registerEffects();
		registerCreativeTabs();

		LOGGER.info("Hello Fabric world!");
	}
}