package net.lunarluned.mortem.mixin.entities.bosses;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherBoss.class)
public abstract class WitherHoverMixin {

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void lowerFirstPhaseHover(CallbackInfo ci) {

        WitherBoss wither = (WitherBoss)(Object)this;

        if (wither.getInvulnerableTicks() <= 0 && wither.getHealth() > wither.getMaxHealth() / 2.0F) {

            LivingEntity target = wither.getTarget();

            if (target != null) {

                double desiredY = target.getY() + 1.5D;

                desiredY -= 5.0D;

                Vec3 velocity = wither.getDeltaMovement();

                if (wither.getY() < desiredY) {
                    wither.setDeltaMovement(
                            velocity.x,
                            Math.max(velocity.y, 0.0D) + (0.3D - velocity.y) * 0.3D,
                            velocity.z
                    );
                }
            }
        }
    }
}