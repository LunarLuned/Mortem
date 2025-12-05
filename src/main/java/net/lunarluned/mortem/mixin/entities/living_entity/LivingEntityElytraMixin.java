package net.lunarluned.mortem.mixin.entities.living_entity;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityElytraMixin {

    @ModifyVariable(method = "hurtServer", at = @At("STORE"), ordinal = 0)
    private float scaleElytraCollisionDamage(float originalDamage) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.isFallFlying()) {
            return originalDamage * 2.0f;
        }

        return originalDamage;
    }
}