package net.lunarluned.mortem;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.lunarluned.mortem.MortemCreativeModeTab.registerCreativeTabs;
import static net.lunarluned.mortem.block.ModBlocks.registerModBlocks;
import static net.lunarluned.mortem.effect.ModEffects.registerEffects;
import static net.lunarluned.mortem.item.ModItemGroups.registerItemGroups;
import static net.lunarluned.mortem.item.ModItems.registerModItems;
import static net.lunarluned.mortem.potion.ModPotions.registerPotions;

public class Mortem implements ModInitializer {
	public static final String MOD_ID = "mortem";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		registerModBlocks();
		registerModItems();
		registerItemGroups();
		registerEffects();
		registerCreativeTabs();
		registerPotions();

		LOGGER.info("Post Mortem.");
	}

}