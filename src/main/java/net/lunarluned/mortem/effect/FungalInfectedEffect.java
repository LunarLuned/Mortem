package net.lunarluned.mortem.effect;

import net.lunarluned.mortem.sounds.MortemSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class FungalInfectedEffect extends MobEffect {
    protected FungalInfectedEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        if (livingEntity.hasEffect(ModEffects.FUNGALLY_INFECTED)) {
            if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(3600)) {
                if (livingEntity.tickCount % 10 == 0) { // every 1/2 second
                    livingEntity.level().playLocalSound(livingEntity, MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(6000)) {
                if (livingEntity.tickCount % 10 == 0) { // every 1/2 second
                    livingEntity.level().playLocalSound(livingEntity, MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(10800)) {
                if (livingEntity.tickCount % 20 == 0) { // every 1 second
                    livingEntity.level().playLocalSound(livingEntity, MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(12000)) {
                if (livingEntity.tickCount % 50 == 0) { // every 1 seconds
                    livingEntity.level().playLocalSound(livingEntity, MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(15600)) {
                if (livingEntity.tickCount % 100 == 0) { // every 2 seconds
                    livingEntity.level().playLocalSound(livingEntity, MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
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
