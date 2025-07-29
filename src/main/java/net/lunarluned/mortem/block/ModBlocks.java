package net.lunarluned.mortem.block;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.block.custom.CrumblingBlock;
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
    public static final Block CRUMBLING_STONE = registerBlock("crumbling_stone",
            new CrumblingBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "crumbling_stone")))
                    .strength(1f).sound(SoundType.STONE)));

    public static final Block CRUMBLING_DEEPSLATE = registerBlock("crumbling_deepslate",
            new CrumblingBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "crumbling_deepslate")))
                    .strength(1f).sound(SoundType.DEEPSLATE)));

    public static final Block CRUMBLING_NETHERRACK = registerBlock("crumbling_netherrack",
            new CrumblingBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "crumbling_netherrack")))
                    .strength(1f).sound(SoundType.NETHERRACK)));

    public static final Block CRUMBLING_END_STONE = registerBlock("crumbling_end_stone",
            new CrumblingBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(Mortem.MOD_ID, "crumbling_end_stone")))
                    .strength(1f).sound(SoundType.STONE)));


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