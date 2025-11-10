package net.lunarluned.mortem.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

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
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(6000)) {
                if (livingEntity.tickCount % 10 == 0) { // every 1/2 second
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 4.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(10800)) {
                if (livingEntity.tickCount % 20 == 0) { // every 1 second
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(12000)) {
                if (livingEntity.tickCount % 50 == 0) { // every 1 seconds
                    livingEntity.hurtServer(serverLevel, livingEntity.damageSources().magic(), 2.0F);
                }
            } else if (Objects.requireNonNull(livingEntity.getEffect(ModEffects.FUNGALLY_INFECTED)).endsWithin(15600)) {
                if (livingEntity.tickCount % 100 == 0) { // every 2 seconds
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
