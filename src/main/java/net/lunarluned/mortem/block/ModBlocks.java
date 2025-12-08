package net.lunarluned.mortem.block;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.block.custom.ApplePieBlock;
import net.lunarluned.mortem.block.custom.CopperRailBlock;
import net.lunarluned.mortem.block.custom.HardenedIronOre;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

    public static final Block APPLE_PIE = registerBlockWithItemThatStacksToOne("apple_pie",
            new ApplePieBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "apple_pie")))));

    public static final Block COPPER_RAIL = registerBlock("copper_rail",
            new CopperRailBlock(BlockBehaviour.Properties.of().strength(.7f).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "copper_rail"))).sound(SoundType.METAL).noCollision()));

    public static final Block HARDENED_IRON_ORE = registerBlock("hardened_iron_ore",
            new HardenedIronOre(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "hardened_iron_ore")))
                    .strength(8f).explosionResistance(1).sound(SoundType.STONE)));

    public static final Block HARDENED_DEEPSLATE_IRON_ORE = registerBlock("hardened_deepslate_iron_ore",
            new HardenedIronOre(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "hardened_deepslate_iron_ore")))
                    .strength(12f).explosionResistance(4).sound(SoundType.DEEPSLATE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, name), block);
    }

    private static Block registerBlockWithItemThatStacksToOne(String name, Block block) {
        registerBlockItemStacksTo1(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name),
                new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name))).useBlockDescriptionPrefix()));
    }

    private static void registerBlockItemStacksTo1(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name),
                new BlockItem(block, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name))).useBlockDescriptionPrefix()));
    }

    public static void registerModBlocks() {
    }
}