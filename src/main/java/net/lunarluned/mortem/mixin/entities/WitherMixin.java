package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherBoss.class)
public abstract class WitherMixin extends LivingEntity {

    protected WitherMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f);

    @Inject(at = @At("HEAD"), method = "createAttributes", cancellable = true)
    private static void mortem_changeWitherAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // buff wither's armor from 0 to 10
        cir.setReturnValue(Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, (double)300.0F).add(Attributes.MOVEMENT_SPEED, (double)0.6F).add(Attributes.FLYING_SPEED, (double)0.6F).add(Attributes.FOLLOW_RANGE, (double)40.0F).add(Attributes.ARMOR, (double)10.0F));
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", cancellable = true)
    public void mortem_capDamageOnHurtServerWither(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
            if (f > 20) {
                cir.setReturnValue(hurtServer(serverLevel, damageSource, 20));
            }
        }
    }
