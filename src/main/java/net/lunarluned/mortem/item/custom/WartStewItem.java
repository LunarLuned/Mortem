package net.lunarluned.mortem.item.custom;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WartStewItem extends Item {
    public WartStewItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {

        if (livingEntity.getRandom().nextInt(10) <= 5) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.IMMUNE, 20, 0));
        }

        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.literal("Has a chance to apply:").withStyle(ChatFormatting.DARK_PURPLE));
        consumer.accept(Component.translatable(this.getDescriptionId() + ".desc.applies").withStyle(ChatFormatting.BLUE));
    }
}
