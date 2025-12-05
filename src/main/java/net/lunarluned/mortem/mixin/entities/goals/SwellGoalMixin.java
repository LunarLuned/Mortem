package net.lunarluned.mortem.mixin.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.Control;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

@Mixin(SwellGoal.class)
public abstract class SwellGoalMixin extends Goal {

    @Shadow
    @Nullable
    private LivingEntity target;

    @Shadow
    @Final
    private Creeper creeper;

    @Shadow
    public abstract void stop();

    @Inject(method = "<init>", at = @At("TAIL"))
    public void mortem_creeperIgniteWhileMoving(Creeper creeper, CallbackInfo ci) {
        EnumSet<Flag> empty = EnumSet.noneOf(Flag.class);
        this.setFlags(empty);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void mortem_BetterCreeperIgnite(CallbackInfo ci) {
        double d = this.creeper.distanceToSqr(this.target);
        if (this.target == null) {
            this.creeper.setSwellDir(-1);
        } else if (d > 16.0D) {
            this.creeper.setSwellDir(-1);
        } else {
            this.creeper.setSwellDir(1);
        }
    }

}