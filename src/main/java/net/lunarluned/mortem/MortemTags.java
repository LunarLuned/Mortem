package net.lunarluned.mortem;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MortemTags {
    public static final TagKey<Block> ARROW_BREAKABLE = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("mortem", "arrow_breakable"));
}