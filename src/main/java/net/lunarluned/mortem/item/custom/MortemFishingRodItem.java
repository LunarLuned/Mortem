package net.lunarluned.mortem.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.w3c.dom.Text;

import java.util.List;
import java.util.function.Consumer;

public class MortemFishingRodItem extends FishingRodItem {

    private final float speedMultiplier;
    private final float treasureMultiplier;

    public MortemFishingRodItem(
            Properties properties,
            float speedMultiplier,
            float treasureMultiplier
    ) {
        super(properties);
        this.speedMultiplier = speedMultiplier;
        this.treasureMultiplier = treasureMultiplier;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public float getTreasureMultiplier() {
        return treasureMultiplier;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.literal(String.format("Fishing Speed: +%.0f%%", (speedMultiplier - 1.0F) * 100.0F)).withStyle(ChatFormatting.GREEN));
        consumer.accept(Component.literal(String.format("Treasure Chance: %+.0f%%", (treasureMultiplier - 1.0F) * 100.0F)).withStyle(treasureMultiplier >= 1.0F ? ChatFormatting.AQUA : ChatFormatting.RED));
        }
    }