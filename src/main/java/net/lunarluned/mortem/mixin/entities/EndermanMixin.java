package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class EndermanMixin extends Monster {


    @Shadow protected abstract boolean teleport();

    protected EndermanMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "createAttributes", cancellable = true)
    private static void mortem_changeEndermanAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // buff enderman's armor from 0 to 4
        cir.setReturnValue(Monster.createMonsterAttributes().add(Attributes.ARMOR, 4).add(Attributes.MAX_HEALTH, (double)40.0F).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.ATTACK_DAMAGE, (double)7.0F).add(Attributes.FOLLOW_RANGE, (double)64.0F).add(Attributes.STEP_HEIGHT, (double)1.0F));
    }

    @Inject(at = @At("HEAD"), method = "hurtServer")
    public void mortem_hurtServerEndermanBreakBoat(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof Boat) {
            this.getVehicle().hurt(damageSource, 10);
        }
    }
}
