package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin {

    @ModifyArg(
            method = "finalizeSpawn",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V")
    )
    private double mortem_wSkeletonAttackDamage(double original) {
        return -1;
    }
}