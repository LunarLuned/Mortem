package net.lunarluned.mortem.world.entity.ai.goal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HeavyLeapAtGoal extends Goal {

    private final Spider spider;
    private LivingEntity target;

    private int leapCooldown = 0;

    public HeavyLeapAtGoal(Spider spider) {
        this.spider = spider;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (leapCooldown > 0) {
            leapCooldown--;
            return false;
        }

        target = spider.getTarget();
        return target != null && spider.distanceTo(target) > 6.0F && spider.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        leapCooldown = 40;
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        if (target == null) return;


        if (!spider.level().isClientSide()) {
            ((ServerLevel) spider.level()).sendParticles(
                    ParticleTypes.ITEM_COBWEB,
                    spider.getX(),
                    spider.getY(),
                    spider.getZ(),
                    10,
                    0.2, 0.2, 0.2, 0.01
            );
        }
        if (!spider.level().isClientSide()) {
            ((ServerLevel) spider.level()).sendParticles(
                    ParticleTypes.ITEM_COBWEB,
                    target.getX(),
                    target.getY(),
                    target.getZ(),
                    10,
                    0.2, 0.2, 0.2, 0.01
            );
        }
        // turn to face the target
        spider.getLookControl().setLookAt(target, 30.0F, 30.0F);

        Vec3 direction = new Vec3(
                target.getX() - spider.getX(),
                target.getY(1.5D) - spider.getY(),
                target.getZ() - spider.getZ()
        ).normalize().scale(1.5);

        spider.setDeltaMovement(direction);
    }
}