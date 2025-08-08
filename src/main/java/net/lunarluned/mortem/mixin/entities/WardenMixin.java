package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin extends LivingEntity {

    protected WardenMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f);

    @Inject(at = @At("HEAD"), method = "createAttributes", cancellable = true)
    private static void mortem_changeWardenAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // buff warden's armor from 0 to 15
        cir.setReturnValue(Monster.createMonsterAttributes().add(Attributes.ARMOR, 15).add(Attributes.MAX_HEALTH, (double)500.0F).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.KNOCKBACK_RESISTANCE, (double)1.0F).add(Attributes.ATTACK_KNOCKBACK, (double)1.5F).add(Attributes.ATTACK_DAMAGE, (double)30.0F).add(Attributes.FOLLOW_RANGE, (double)24.0F));
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/monster/warden/Warden;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", cancellable = true)
    public void mortem_capDamageOnHurtServerWarden(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
            if (f > 20) {
                cir.setReturnValue(hurtServer(serverLevel, damageSource, 20));
            }
        }
    }
