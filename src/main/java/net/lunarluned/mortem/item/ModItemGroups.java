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
                        entries.accept(ModBlocks.HARDENED_DEEPSLATE_IRON_ORE);
                    }).build());

    public static void registerItemGroups() {
        Mortem.LOGGER.info("Registering Item Groups for " + Mortem.MOD_ID);
    }
}