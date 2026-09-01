package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {

    @Inject(method = "isWearingSafeArmor", at = @At("HEAD"), cancellable = true)
    private static void enigma$armorPointThreshold(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity.getArmorValue() >= 18) {
            cir.setReturnValue(true);
        }
    }
}