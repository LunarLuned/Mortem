package net.lunarluned;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ModLootTables {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, entity) -> {

            if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS)) {
                ItemStack stack = player.getMainHandItem();

                int hoeSeedLuck = 0;

                if (stack.is(Items.WOODEN_HOE)) {
                    hoeSeedLuck = 30;
                } else if (stack.is(Items.STONE_HOE)) {
                    hoeSeedLuck = 55;
                } else if (stack.is(Items.IRON_HOE)) {
                    hoeSeedLuck = 60;
                } else if (stack.is(Items.COPPER_HOE)) {
                    hoeSeedLuck = 65;
                } else if (stack.is(Items.GOLDEN_HOE)) {
                    hoeSeedLuck = 95;
                } else if (stack.is(Items.DIAMOND_HOE)) {
                    hoeSeedLuck = 75;
                } else if (stack.is(Items.NETHERITE_HOE)) {
                    hoeSeedLuck = 80;
                }

                if (player.getMainHandItem().is(Items.WOODEN_HOE)
                        || player.getMainHandItem().is(Items.STONE_HOE)
                        || player.getMainHandItem().is(Items.IRON_HOE)
                        || player.getMainHandItem().is(Items.GOLDEN_HOE)
                        || player.getMainHandItem().is(Items.DIAMOND_HOE)
                        || player.getMainHandItem().is(Items.NETHERITE_HOE)) {

                    // “almost always”
                    if (hoeSeedLuck >= level.getRandom().nextInt(100)) {
                        ItemEntity itemEntity = new ItemEntity(
                                level,
                                pos.getX() + 0.5,
                                pos.getY() + 0.5,
                                pos.getZ() + 0.5,
                                new ItemStack(Items.WHEAT_SEEDS)
                        );

                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        });
    }
}