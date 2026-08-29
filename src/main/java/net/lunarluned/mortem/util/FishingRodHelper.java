package net.lunarluned.mortem.util;

import net.lunarluned.mortem.item.custom.MortemFishingRodItem;
import net.minecraft.world.item.ItemStack;

public final class FishingRodHelper {

    private FishingRodHelper() {
    }

    public static float getSpeedMultiplier(ItemStack stack) {
        if (stack.getItem() instanceof MortemFishingRodItem rod) {
            return rod.getSpeedMultiplier();
        }

        return 1.0F;
    }

    public static float getTreasureMultiplier(ItemStack stack) {
        if (stack.getItem() instanceof MortemFishingRodItem rod) {
            return rod.getTreasureMultiplier();
        }

        return 1.0F;
    }
}