package net.lunarluned.mortem.mixin.entities.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public class HappyGhastMixin {

        @Inject(method = "addPassenger", at = @At("TAIL"))
        private void mortem_onPassengerAdded(Entity passenger, CallbackInfo ci) {
            mortem_updateSpeed();
        }

        @Inject(method = "removePassenger", at = @At("TAIL"))
        private void mortem_onPassengerRemoved(Entity passenger, CallbackInfo ci) {
            mortem_updateSpeed();
        }

        @Unique
        private void mortem_updateSpeed() {
            HappyGhast happyGhast = (HappyGhast)(Object)this;

            AttributeInstance flyingSpeed = happyGhast.getAttribute(Attributes.FLYING_SPEED);

            if (flyingSpeed == null) {
                return;
            }

            int riders = happyGhast.getPassengers().size();

            double speed = switch (riders) {
                case 1 -> 0.085D;
                case 2 -> 0.07D;
                case 3 -> 0.065D;
                default -> 0.045D;
            };

            flyingSpeed.setBaseValue(speed);
        }
}
