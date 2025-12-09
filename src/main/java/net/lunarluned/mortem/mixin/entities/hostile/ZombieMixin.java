package net.lunarluned.mortem.mixin.entities.hostile;

import net.lunarluned.mortem.world.entity.ai.goal.EatCropGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Mob {

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void modifyFollowRange(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();

        builder.add(Attributes.FOLLOW_RANGE, 64);

        cir.setReturnValue(builder);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void onRegisterGoals(CallbackInfo ci) {
        Zombie self = (Zombie) (Object) this;
        self.targetSelector.addGoal(5, new EatCropGoal(self, 1.1D));
        self.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(self, Animal.class, true));
    }
}

