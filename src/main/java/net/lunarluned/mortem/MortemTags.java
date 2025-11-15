package net.lunarluned.mortem;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MortemTags {
    public static final TagKey<Block> ARROW_BREAKABLE = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("mortem", "arrow_breakable"));
    public static final TagKey<Block> FLAMMABLE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("mortem", "flammable_blocks"));
    public static final TagKey<Block> EXPLOSION_PROOF = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("mortem", "explosion_proof_blocks"));



    public static final TagKey<EntityType<?>> CANNOT_BE_ZOMBIFIED = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild("mortem", "cannot_be_zombified"));
    public static final TagKey<EntityType<?>> FUNGUS_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild("mortem", "fungus_immune"));


    public static final TagKey<Item> TORCHES = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("mortem", "torches"));
    public static final TagKey<Item> METAL_ITEMS = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("mortem", "metal_items"));
    public static final TagKey<Item> JAMS = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("mortem", "jams"));
}