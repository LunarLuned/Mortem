package net.lunarluned.mortem.mixin.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ElytraDamageCapMixin {

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void capSpearDamage(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {

        Entity attacker = source.getEntity();

        if (attacker instanceof Player player) {

            if (player.isFallFlying()) {

                float capped = Math.min(damage, 20.0f);

                if (damage > 20.0f) {
                    damage = capped;
                    ((LivingEntity)(Object)this).hurt(source, damage);
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
