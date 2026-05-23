package net.lunarluned.mortem.mixin.entities.passive;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {

    @Mutable
    @Final
    @Shadow
    protected final Mob mob;

    public TemptGoalMixin(Mob mob) {
        this.mob = mob;
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void mortem_preventTemptWhenFed(CallbackInfoReturnable<Boolean> cir) {


        if (mob instanceof Animal animal) {
            if (animal.getAge() > 0 && !animal.isBaby()) {
                cir.setReturnValue(false);
            }
        }
}
}
