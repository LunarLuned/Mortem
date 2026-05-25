package net.lunarluned.mortem.mixin.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public abstract class SnowballMixin {

    @Inject(method = "onHitEntity", at = @At("TAIL"))
    private void igniteTarget(EntityHitResult hitResult, CallbackInfo ci) {

        Entity entity = hitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {

            Snowball snowball = (Snowball)(Object)this;

            if (snowball.isOnFire()) {
                livingEntity.igniteForSeconds(5);
            }
        }
    }
}