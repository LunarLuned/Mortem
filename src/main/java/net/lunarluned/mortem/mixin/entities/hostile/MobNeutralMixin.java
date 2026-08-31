package net.lunarluned.mortem.mixin.entities.hostile;

import net.lunarluned.mortem.misc.accessor.MortemAngerAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Mob.class)
public abstract class MobNeutralMixin {

    @Inject(
            method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mortem_blockUnprovokedPlayerAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        Mob self = (Mob) (Object) this;
        if (!(self instanceof AbstractIllager)) return;

        LivingEntity angryAt = ((MortemAngerAccessor) self).mortem_getAngryAt();
        if (target instanceof Player
                && self.level().dimension() == Level.NETHER
                && target != angryAt) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "customServerAiStep", at = @At("TAIL"))
    private void mortem_maintainAngerInNether(CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        if (!(self instanceof AbstractIllager)) return;
        if (self.level().dimension() != Level.NETHER) return;

        MortemAngerAccessor accessor = (MortemAngerAccessor) self;
        LivingEntity angryAt = accessor.mortem_getAngryAt();

        if (angryAt != null && !angryAt.isAlive()) {
            accessor.mortem_setAngryAt(null);
            return;
        }

        if (angryAt != null && self.getTarget() != angryAt) {
            self.setTarget(angryAt);
        }
    }
}