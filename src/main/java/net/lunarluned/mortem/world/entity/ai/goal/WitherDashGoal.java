package net.lunarluned.mortem.world.entity.ai.goal;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WitherDashGoal extends Goal {

    private final WitherBoss wither;
    private LivingEntity target;

    private int dashCooldown = 0;
    private int dashTicks = 0;

    public WitherDashGoal(WitherBoss wither) {
        this.wither = wither;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dashCooldown > 0) {
            dashCooldown--;
            return false;
        }

        target = wither.getTarget();
        return target != null && wither.distanceTo(target) > 4.0F && wither.getRandom().nextInt(40) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return dashTicks > 0 && target != null;
    }

    @Override
    public void start() {
        dashTicks = 35;
        dashCooldown = 100;
    }

    @Override
    public void stop() {
        dashTicks = 0;
    }

    @Override
    public void tick() {
        if (target == null) return;

        // turn to face the target
        wither.getLookControl().setLookAt(target, 30.0F, 30.0F);

        Vec3 direction = new Vec3(
                target.getX() - wither.getX(),
                target.getY(-1.5D) - wither.getY(),
                target.getZ() - wither.getZ()
        ).normalize().scale(1.5);

        // apply velocity
        wither.setDeltaMovement(direction);
        wither.level().explode(wither, wither.getX(), wither.getY(), wither.getZ(), 0.4F, false, Level.ExplosionInteraction.MOB);
        if (!wither.level().isClientSide()) {
            ((ServerLevel) wither.level()).sendParticles(
                    ParticleTypes.PORTAL,
                    wither.getX(),
                    wither.getY(),
                    wither.getZ(),
                    10,
                    0.2, 0.2, 0.2, 0.01
            );
        }
        dashTicks--;

        // actually damage if close
        if (wither.distanceTo(target) < 1.5F) {
            target.hurt(wither.damageSources().mobAttack(wither), 10.0F);
            target.addEffect(new MobEffectInstance(ModEffects.STAGNATED, 200, 0));
            dashTicks = 0;
        }
    }
}