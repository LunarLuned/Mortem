package net.lunarluned.mortem.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EnhancedImmunityEffect extends MobEffect {
    public EnhancedImmunityEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        if (livingEntity.hasEffect(MobEffects.POISON)) {
            livingEntity.removeEffect(MobEffects.POISON);
        }
        if (livingEntity.hasEffect(MobEffects.WITHER)) {
            livingEntity.removeEffect(MobEffects.WITHER);
        }
        if (livingEntity.hasEffect(MobEffects.MINING_FATIGUE)) {
            livingEntity.removeEffect(MobEffects.MINING_FATIGUE);
        }
        if (livingEntity.hasEffect(MobEffects.NAUSEA)) {
            livingEntity.removeEffect(MobEffects.NAUSEA);
        }
        if (livingEntity.hasEffect(ModEffects.INFECTED)) {
            livingEntity.removeEffect(ModEffects.INFECTED);
        }
        if (livingEntity.hasEffect(MobEffects.HUNGER)) {
            livingEntity.removeEffect(MobEffects.HUNGER);
        }
        if (livingEntity.hasEffect(ModEffects.FUNGALLY_INFECTED)) {
            livingEntity.removeEffect(ModEffects.FUNGALLY_INFECTED);
        }
        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int i) {

        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.HOSTILE, 1.0F, 3);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
        super.onEffectStarted(livingEntity, i);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}
