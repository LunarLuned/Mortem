package net.lunarluned.mortem.mixin.entities.bosses.dragon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends LivingEntity {

    protected EnderDragonMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
            if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                return true;
            }
        return super.isInvulnerableTo(serverLevel, damageSource);
    }

    @Inject(at = @At("HEAD"), method = "createAttributes", cancellable = true)
    private static void mortem_changeDragonAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // buff dragon's health to 300, adds 20 armor
            cir.setReturnValue(Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.ARMOR, 15.0D));
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;hurt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/boss/EnderDragonPart;Lnet/minecraft/world/damagesource/DamageSource;F)Z", cancellable = true)
    public void mortem_capDamageOnHurtServerEnderDragon(ServerLevel serverLevel, EnderDragonPart enderDragonPart, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        // caps amt of damage the dragon can take at a time to 35
        if (f > 35) {
            cir.setReturnValue(hurtServer(serverLevel, damageSource, 35));
        }
    }
}
