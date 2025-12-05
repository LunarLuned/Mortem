package net.lunarluned.mortem;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.lunarluned.mortem.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.lunarluned.mortem.MortemCreativeModeTab.registerCreativeTabs;
import static net.lunarluned.mortem.block.ModBlocks.registerModBlocks;
import static net.lunarluned.mortem.effect.ModEffects.registerEffects;
import static net.lunarluned.mortem.enchantments.ModEnchantmentEffects.registerEnchantmentEffects;
import static net.lunarluned.mortem.item.ModItemGroups.registerItemGroups;
import static net.lunarluned.mortem.item.ModItems.registerModItems;
import static net.lunarluned.mortem.potion.ModPotions.registerPotions;
import static net.lunarluned.mortem.sounds.MortemSoundEvents.registerSounds;

public class Mortem implements ModInitializer {
	public static final String MOD_ID = "mortem";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation id(String name) {
		return ResourceLocation.tryBuild(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		registerModBlocks();
		registerModItems();
		registerItemGroups();
		registerEffects();
		registerCreativeTabs();
		registerPotions();
		registerSounds();

		registerEnchantmentEffects();

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(ModPotions.INFECTED, Ingredient.of(Items.GOLDEN_APPLE), ModPotions.IMMUNE);
		});

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(ModPotions.INFECTED, Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), ModPotions.EXTENDED_IMMUNITY);
		});

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.MUNDANE, Ingredient.of(Items.ROTTEN_FLESH), ModPotions.INFECTED);
		});

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.THICK, Ingredient.of(Items.ROTTEN_FLESH), ModPotions.INFECTED);
		});

		// Crops broken without needed tools has a chance to trample the farmland
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (world.isClientSide()) return;

			Block block = state.getBlock();

			if (!(block instanceof CropBlock)) return;

			ItemStack tool = player.getMainHandItem();

			if (tool.is(MortemTags.FARMING_TOOLS)) return;
			if (world.getRandom().nextInt(9) < 6) return;

			BlockPos below = pos.below();
			if (world.getBlockState(below).is(Blocks.FARMLAND)) {
				world.setBlockAndUpdate(below, Blocks.DIRT.defaultBlockState());
			}
		});

		LOGGER.info("Post Mortem.");
	}

}