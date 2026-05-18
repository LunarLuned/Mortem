package net.lunarluned.mortem.world.entity.ai.goal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class WitherSlamdownGoal extends Goal {

    private final WitherBoss wither;

    private int phaseTicks;

    private boolean slamming;

    private Vec3 slamDirection = Vec3.ZERO;

    public WitherSlamdownGoal(WitherBoss wither) {
        this.wither = wither;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {

        LivingEntity target = wither.getTarget();

        return target != null && target.isAlive() && wither.getRandom().nextInt(250) == 0;
    }

    @Override
    public void start() {

        phaseTicks = 0;

        slamming = false;

        wither.level().playSound(null, wither.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 4.0F, 0.7F);
    }

    @Override
    public boolean canContinueToUse() {

        return phaseTicks < 80;
    }

    @Override
    public void tick() {

        phaseTicks++;

        LivingEntity target = wither.getTarget();

        if (target == null) return;

        if (!slamming) {

            Vec3 hoverPos = new Vec3(target.getX(), target.getY() + 18, target.getZ());

            Vec3 move = hoverPos.subtract(wither.position()).normalize().scale(0.22);

            wither.setDeltaMovement(move);

            wither.getLookControl().setLookAt(target);

            if (wither.level() instanceof ServerLevel serverLevel) {

                serverLevel.sendParticles(ParticleTypes.SMOKE, wither.getX(), wither.getY(), wither.getZ(), 8, 2.5, 2.5, 2.5, 0.02
                );
            }

            if (phaseTicks >= 30) {

                slamDirection = target.position()
                        .subtract(wither.position())
                        .normalize();

                slamming = true;

                phaseTicks = 0;

                wither.level().playSound(
                        null,
                        wither.blockPosition(),
                        SoundEvents.ENDER_DRAGON_GROWL,
                        SoundSource.HOSTILE,
                        5.0F,
                        0.5F
                );
            }

            return;
        }

        Vec3 slamVelocity = slamDirection.scale(2.8);

        slamVelocity = slamVelocity.add(0, -1.5, 0);

        wither.setDeltaMovement(slamVelocity);

        if (wither.level() instanceof ServerLevel serverLevel) {

            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, wither.getX(), wither.getY(), wither.getZ(), 12, 0.3, 0.3, 0.3, 0.03
            );
        }

        if (wither.onGround() || wither.getBoundingBox().intersects(target.getBoundingBox())) {

            explodeImpact();

            phaseTicks = 600;
        }
    }

    private void explodeImpact() {

        Level level = wither.level();

        level.explode(wither, wither.getX(), wither.getY(), wither.getZ(), 3.0F, Level.ExplosionInteraction.MOB
        );

        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                wither.getBoundingBox().inflate(8)
        );

        for (LivingEntity entity : entities) {

            if (entity == wither) continue;

            entity.hurtServer((ServerLevel) level, wither.damageSources().mobAttack(wither), 14.0F);

            Vec3 knockback = entity.position().subtract(wither.position()).normalize().scale(4.2).add(0, 2.5, 0);

            entity.setDeltaMovement(knockback);
        }

        level.playSound(null, wither.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 5.0F, 0.8F);
    }

    @Override
    public void stop() {

        wither.setDeltaMovement(Vec3.ZERO);

        slamming = false;
    }
}