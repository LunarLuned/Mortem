package net.lunarluned.mortem;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MortemTags {
    public static final TagKey<Block> ARROW_BREAKABLE = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("mortem", "arrow_breakable"));

    public static final TagKey<EntityType<?>> ABLE_TO_BE_ZOMBIFIED = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild("mortem", "able_to_be_zombified"));

    public static final TagKey<Item> TORCHES = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("mortem", "torches"));
}