package net.lunarluned.mortem.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EnhancedLuckEffect extends MobEffect {
    public EnhancedLuckEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        if (livingEntity.hasEffect(MobEffects.LEVITATION)) {
            livingEntity.removeEffect(MobEffects.LEVITATION);
        }
        if (livingEntity.hasEffect(MobEffects.DARKNESS)) {
            livingEntity.removeEffect(MobEffects.DARKNESS);
        }
        if (livingEntity.hasEffect(MobEffects.INFESTED)) {
            livingEntity.removeEffect(MobEffects.INFESTED);
        }
        if (livingEntity.hasEffect(MobEffects.OOZING)) {
            livingEntity.removeEffect(MobEffects.OOZING);
        }
        if (livingEntity.hasEffect(MobEffects.SLOW_FALLING)) {
            livingEntity.removeEffect(MobEffects.SLOW_FALLING);
        }
        if (livingEntity.hasEffect(MobEffects.WEAVING)) {
            livingEntity.removeEffect(MobEffects.WEAVING);
        }
        if (livingEntity.hasEffect(MobEffects.WIND_CHARGED)) {
            livingEntity.removeEffect(MobEffects.WIND_CHARGED);
        }
        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int i) {

        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.HOSTILE, 1.0F, 3);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0));
        super.onEffectStarted(livingEntity, i);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}
