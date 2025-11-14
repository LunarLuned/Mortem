package net.lunarluned.mortem.item.custom;

import net.lunarluned.mortem.effect.ModEffects;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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


}
