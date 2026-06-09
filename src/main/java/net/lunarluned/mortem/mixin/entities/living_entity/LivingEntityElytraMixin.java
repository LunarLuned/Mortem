package net.lunarluned.mortem.mixin.entities.living_entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityElytraMixin {
    // can cancel flight
    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void mortem_updateFallFlying(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof ServerPlayer player && livingEntity.isFallFlying() && livingEntity.isShiftKeyDown()) {
            player.stopFallFlying();
            ci.cancel();
        }
    }

    // being shot with an arrow cancels flight
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void mortem_beingHurtCancelsElytra(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof ServerPlayer player && livingEntity.isFallFlying() && source.is(DamageTypes.ARROW)) {
            player.stopFallFlying();
            cir.cancel();
        }
    }

    // double flying damage
    @ModifyVariable(method = "hurtServer", at = @At("STORE"), ordinal = 0, argsOnly = true)
    private float mortem_scaleElytraCollisionDamage(float originalDamage) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.isFallFlying()) {
            return originalDamage * 2.0f;
        }

        return originalDamage;
    }
}