package net.lunarluned.mortem.mixin.entities.projectiles;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireball.class)
public abstract class SmallFireballMixin {

    @WrapOperation(
            method = "onHitEntity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z")
    )
    private boolean enigma$nerfPlayerDamage(Entity instance, ServerLevel serverLevel, DamageSource damageSource, float v, Operation<Boolean> original) {
        if (instance instanceof Player) {
            v = 1f;
        }
        return original.call(instance, serverLevel, damageSource, v);
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"), cancellable = true)
    private void enigma$noBlazeFire(BlockHitResult hitResult, CallbackInfo ci) {
        if (((SmallFireball) (Object) this).getOwner() instanceof Blaze) {
            ci.cancel();
        }
    }
}