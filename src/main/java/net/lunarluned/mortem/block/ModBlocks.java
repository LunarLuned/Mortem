package net.lunarluned.mortem.block;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.block.custom.CrumblingBlock;
import net.lunarluned.mortem.block.custom.HardenedIronOre;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
public class ModBlocks {

    public static final Block HARDENED_IRON_ORE = registerBlock("hardened_iron_ore",
            new HardenedIronOre(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "hardened_iron_ore")))
                    .strength(10f).sound(SoundType.STONE)));

    public static final Block HARDENED_DEEPSLATE_IRON_ORE = registerBlock("hardened_deepslate_iron_ore",
            new HardenedIronOre(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "hardened_deepslate_iron_ore")))
                    .strength(16f).sound(SoundType.DEEPSLATE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name),
                new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name))).useBlockDescriptionPrefix()));
    }

    public static void registerModBlocks() {
    }
}