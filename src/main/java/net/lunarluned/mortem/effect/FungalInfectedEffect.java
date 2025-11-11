package net.lunarluned.mortem.effect;

import net.lunarluned.mortem.sounds.MortemSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class FungalInfectedEffect extends MobEffect {
    protected FungalInfectedEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {

        int randomValue = 0;
        int randomValue2 = 0;

        if (livingEntity.tickCount % 100 == 0) {
            randomValue = Mth.nextInt(RandomSource.create(), 1, 10);
            randomValue2 = Mth.nextInt(RandomSource.create(), 1, 4);
        }

        if (randomValue <= 5) {
            switch (randomValue2) {
                case 1:
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 110, 0));
                    break;
                case 2:
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 120 , 0));
                    break;
                case 3:
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                    break;
                case 4:
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 150, 0));
                    break;
            }
        }

            if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(3600)) {
                if (livingEntity.tickCount % 10 == 0) { // every 1/2 second
                    serverLevel.playSound(null, livingEntity.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(6000)) {
                if (livingEntity.tickCount % 10 == 0) { // every 1/2 second
                    serverLevel.playSound(null, livingEntity.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(10800)) {
                if (livingEntity.tickCount % 20 == 0) { // every 1 second
                    serverLevel.playSound(null, livingEntity.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(12000)) {
                if (livingEntity.tickCount % 50 == 0) { // every 1 seconds
                    serverLevel.playSound(null, livingEntity.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(15600)) {
                if (livingEntity.tickCount % 100 == 0) { // every 2 seconds
                    serverLevel.playSound(null, livingEntity.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
        }
        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }



    public void onMobHurt(ServerLevel serverLevel, LivingEntity l, int i, DamageSource damageSource, float f) {
    }
}
