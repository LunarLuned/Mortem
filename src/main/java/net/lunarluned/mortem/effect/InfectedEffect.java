package net.lunarluned.mortem.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class InfectedEffect extends MobEffect {
    protected InfectedEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
            int randomValue = Mth.nextInt(RandomSource.create(), 1, 100);
        if (randomValue < 5) serverLevel.playSound(null, livingEntity.blockPosition(), SoundEvents.ZOMBIE_AMBIENT, SoundSource.HOSTILE, 0.2F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
        if (amplifier >= 0) {
                if (randomValue < 2) {
                    randomValue = Mth.nextInt(RandomSource.create(), 1, 4);
                    switch (randomValue) {
                        case 1:
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                            break;
                        case 2:
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 400, 0));
                            break;
                        case 3:
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 800, 0));
                            break;
                        case 4:
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 300, 0));
                            break;
                    }
                }
                if (amplifier >= 1) {
                    if (randomValue < 3) {
                        randomValue = Mth.nextInt(RandomSource.create(), 1, 4);
                        switch (randomValue) {
                            case 1:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 1));
                                break;
                            case 2:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 850, 1));
                                break;
                            case 3:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1200, 1));
                                break;
                            case 4:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 360, 1));
                                break;
                        }
                    }
                    if (amplifier >= 2) {
                        if (randomValue < 5) {
                            randomValue = Mth.nextInt(RandomSource.create(), 1, 4);
                            switch (randomValue) {
                                case 1:
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2400, 2));
                                    break;
                                case 2:
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 2400, 2));
                                    break;
                                case 3:
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 2400, 2));
                                    break;
                                case 4:
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 2400, 2));
                                    break;
                            }
                        }
                    }
                }
            }


        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    //please god make a 'able_to_be_zombified' tag later bro

    public void onMobHurt(ServerLevel serverLevel, LivingEntity l, int i, DamageSource damageSource, float f) {
        if (l.getHealth() < 1) {
            if (l.getHealth() == 1) {
                l.die(damageSource);
            }
            if (l.isUnderWater()) {
                spawnDrowned(serverLevel, l, l.getX(), l.getY(), l.getZ());
            } if (l instanceof Villager || l instanceof Vindicator || l instanceof Witch || l instanceof WanderingTrader) {
                spawnVillagerZombie(serverLevel, l, l.getX(), l.getY(), l.getZ());
            } else {
                spawnZombie(serverLevel, l, l.getX(), l.getY(), l.getZ());
            }
        }

    }

    private void spawnZombie(ServerLevel serverLevel, LivingEntity livingEntity, double d, double e, double f) {
        Zombie zombie = (Zombie) EntityType.ZOMBIE.create(serverLevel, EntitySpawnReason.TRIGGERED);
        if (zombie != null) {
            RandomSource randomSource = livingEntity.getRandom();
            float g = ((float)Math.PI / 2F);
            float h = Mth.randomBetween(randomSource, (-(float)Math.PI / 2F), ((float)Math.PI / 2F));
            Vector3f vector3f = livingEntity.getLookAngle().toVector3f().mul(0.3F).mul(1.0F, 1.5F, 1.0F).rotateY(h);
            zombie.snapTo(d, e, f, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            zombie.setDeltaMovement(new Vec3(vector3f));
            serverLevel.addFreshEntity(zombie);
            zombie.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 1, true, true));
            zombie.addEffect(new MobEffectInstance(MobEffects.SPEED, 350, 0, true, true));
            zombie.playSound(SoundEvents.ZOMBIE_INFECT);
        }
    }
    private void spawnVillagerZombie(ServerLevel serverLevel, LivingEntity livingEntity, double d, double e, double f) {
        ZombieVillager zombie = (ZombieVillager) EntityType.ZOMBIE_VILLAGER.create(serverLevel, EntitySpawnReason.TRIGGERED);
        if (zombie != null) {
            RandomSource randomSource = livingEntity.getRandom();
            float g = ((float)Math.PI / 2F);
            float h = Mth.randomBetween(randomSource, (-(float)Math.PI / 2F), ((float)Math.PI / 2F));
            Vector3f vector3f = livingEntity.getLookAngle().toVector3f().mul(0.3F).mul(1.0F, 1.5F, 1.0F).rotateY(h);
            zombie.snapTo(d, e, f, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            zombie.setDeltaMovement(new Vec3(vector3f));
            serverLevel.addFreshEntity(zombie);
            zombie.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 1, true, true));
            zombie.addEffect(new MobEffectInstance(MobEffects.SPEED, 350, 0, true, true));
            zombie.playSound(SoundEvents.ZOMBIE_INFECT);
        }
    }
    private void spawnDrowned(ServerLevel serverLevel, LivingEntity livingEntity, double d, double e, double f) {
        Drowned zombie = (Drowned) EntityType.DROWNED.create(serverLevel, EntitySpawnReason.TRIGGERED);
        if (zombie != null) {
            RandomSource randomSource = livingEntity.getRandom();
            float g = ((float)Math.PI / 2F);
            float h = Mth.randomBetween(randomSource, (-(float)Math.PI / 2F), ((float)Math.PI / 2F));
            Vector3f vector3f = livingEntity.getLookAngle().toVector3f().mul(0.3F).mul(1.0F, 1.5F, 1.0F).rotateY(h);
            zombie.snapTo(d, e, f, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            zombie.setDeltaMovement(new Vec3(vector3f));
            serverLevel.addFreshEntity(zombie);
            zombie.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 1, true, true));
            zombie.addEffect(new MobEffectInstance(MobEffects.SPEED, 350, 0, true, true));
            zombie.playSound(SoundEvents.ZOMBIE_INFECT);
        }
    }
}
