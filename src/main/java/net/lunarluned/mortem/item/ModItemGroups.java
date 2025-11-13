package net.lunarluned.mortem.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab MORTEM = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            ResourceLocation.parse(Mortem.MOD_ID),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.HARDENED_IRON_ORE))
                    .title(Component.translatable("itemgroup.mortem.mortem"))
                    .displayItems((displayContext, entries) -> {
                        entries.accept(ModBlocks.HARDENED_IRON_ORE);
                        entries.accept(ModBlocks.HARDENED_DEEPSLATE_IRON_ORE);
                        entries.accept(ModItems.DRUG);
                        entries.accept(ModItems.RESIN_CANDY);

                        entries.accept(ModItems.HARD_BOILED_EGG);
                        entries.accept(ModItems.EGG_BOWL);
                        entries.accept(ModItems.SCRAMBLED_EGGS);
                        entries.accept(ModItems.SUSHI_ROLL);
                        entries.accept(ModItems.HOGLIN_TUSK);
                        entries.accept(ModItems.EGG_TUSK);

                        entries.accept(ModItems.SWEET_POTATO);
                        entries.accept(ModItems.RAW_BACON);
                        entries.accept(ModItems.COOKED_BACON);
                        entries.accept(ModItems.BEEF_PATTY);
                        entries.accept(ModItems.COOKED_BEEF_PATTY);
                        entries.accept(ModItems.BURGER);
                        entries.accept(ModItems.BACON_BURGER);
                        entries.accept(ModItems.TOASTED_BREAD);
                        entries.accept(ModItems.TOAST);
                        entries.accept(ModItems.BREAKFAST_SANDWICH);

                        entries.accept(ModItems.SWEET_BERRY_JAM);
                        entries.accept(ModItems.APPLE_JAM);
                        entries.accept(ModItems.CACTUS_JAM);
                        entries.accept(ModItems.COCOA_SPREAD);
                        entries.accept(ModItems.MUSHROOM_PUREE);
                        entries.accept(ModItems.PORK_PATE);

                        entries.accept(ModItems.BERRY_SPREAD_TOAST);
                        entries.accept(ModItems.APPLE_SPREAD_TOAST);
                        entries.accept(ModItems.CACTUS_SPREAD_TOAST);
                        entries.accept(ModItems.COCOA_SPREAD_TOAST);
                        entries.accept(ModItems.MUSHROOM_SPREAD_TOAST);
                        entries.accept(ModItems.PORK_SPREAD_TOAST);

                        entries.accept(ModItems.CRIMSON_STEW);
                        entries.accept(ModItems.WARPED_STEW);
                        entries.accept(ModItems.FUNGAL_STEW);
                        entries.accept(ModItems.MUSHROOM_STEW_TUSK);
                        entries.accept(ModItems.SCRAMBLED_EGGS_TUSK);

                    }).build());


    public static void registerItemGroups() {
        Mortem.LOGGER.info("Registering Item Groups for " + Mortem.MOD_ID);
    }
}