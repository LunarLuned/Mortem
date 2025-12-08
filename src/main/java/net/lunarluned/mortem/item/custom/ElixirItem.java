package net.lunarluned.mortem.item.custom;

import net.lunarluned.mortem.effect.ModEffects;
import net.lunarluned.mortem.item.ModItems;
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

import java.util.List;
import java.util.function.Consumer;

public class ElixirItem extends Item {

    int elixirType;

    public ElixirItem(int elixirType, Properties properties) {
        super(properties);
        this.elixirType = elixirType;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {

        switch (elixirType) {
            case 0:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_IMMUNITY, 20, 0));
                break;
            case 1:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_IMMUNITY, 9600, 0));
                break;
            case 2:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_LUCK, 20, 0));
                break;
            case 3:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_LUCK, 9600, 0));
                break;
            case 4:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_RESISTANCE, 20, 0));
                break;
            case 5:
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ENHANCED_RESISTANCE, 9600, 0));
                break;
        }

            return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.literal("Removes Effects of:").withStyle(ChatFormatting.DARK_PURPLE));
        consumer.accept(Component.translatable(this.getDescriptionId() + ".desc.removes").withStyle(ChatFormatting.RED));
        consumer.accept(Component.translatable(this.getDescriptionId() + ".desc.removes2").withStyle(ChatFormatting.RED));
        consumer.accept(Component.empty());
        consumer.accept(Component.literal("When Applied:").withStyle(ChatFormatting.DARK_PURPLE));
        consumer.accept(Component.translatable(this.getDescriptionId() + ".desc.grants").withStyle(ChatFormatting.BLUE));
    }
}
