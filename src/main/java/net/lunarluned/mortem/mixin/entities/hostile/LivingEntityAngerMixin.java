package net.lunarluned.mortem.mixin.entities.hostile;

import net.lunarluned.mortem.misc.accessor.MortemAngerAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityAngerMixin implements MortemAngerAccessor {

    @Unique
    private LivingEntity mortem_getAngryAt;

    @Unique
    private static final double MOENIGMA_ALERT_RANGE = 32.0;

    @Override
    public LivingEntity mortem_getAngryAt() {
        return this.mortem_getAngryAt;
    }

    @Override
    public void mortem_setAngryAt(LivingEntity target) {
        this.mortem_getAngryAt = target;
    }

    @Inject(method = "setLastHurtByMob", at = @At("HEAD"))
    private void mortem_onHurtByLivingEntity(LivingEntity hurtBy, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof AbstractIllager illager)) return;
        if (!(hurtBy instanceof Player)) return;
        if (self.level().isClientSide()) return;

        this.mortem_getAngryAt = hurtBy;
        mortem_alertNearbyIllagers(illager, hurtBy);
    }

    @Unique
    private void mortem_alertNearbyIllagers(Mob self, LivingEntity attacker) {
        if (!(self.level() instanceof ServerLevel serverLevel)) return;

        AABB range = self.getBoundingBox().inflate(MOENIGMA_ALERT_RANGE);
        for (AbstractIllager other : serverLevel.getEntitiesOfClass(AbstractIllager.class, range,
                e -> e != self && e.isAlive())) {
            MortemAngerAccessor otherAccessor = (MortemAngerAccessor) other;
            if (otherAccessor.mortem_getAngryAt() == null) {
                otherAccessor.mortem_setAngryAt(attacker);
                other.setTarget(attacker);
            }
        }
    }
}