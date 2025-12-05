package net.lunarluned.mortem.mixin.entities.living_entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataSlowedRegenMixin {

    @Shadow
    private int tickTimer;

    // Makes extra-saturation healing cooldown 2 seconds instead of half a second

    @ModifyConstant(
            method = "tick(Lnet/minecraft/server/level/ServerPlayer;)V",
            constant = @Constant(intValue = 10)
    )
    private int mortem_modifyHealingDelay(int original) {
        return 40;
    }

    // Makes extra-saturation based healing have a 40% chance to not heal, making regen slowed

    @Inject(
            method = "tick(Lnet/minecraft/server/level/ServerPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;heal(F)V"
            ), cancellable = true
    )
    private void mortem_saturationHealChance(ServerPlayer serverPlayer, CallbackInfo ci) {


        if (this.tickTimer == 40) {

            double chanceToHeal = 0.60;

            if (serverPlayer.getRandom().nextDouble() > chanceToHeal) {

                // Cancel healing
                ci.cancel();

                this.tickTimer = 0;
                ((FoodData)(Object)this).addExhaustion(6.0F);
            }
        }
    }
}