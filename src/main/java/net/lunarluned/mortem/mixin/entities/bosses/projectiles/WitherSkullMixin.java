package net.lunarluned.mortem.mixin.entities.bosses.projectiles;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkull.class)
public abstract class WitherSkullMixin extends AbstractHurtingProjectile {

    protected WitherSkullMixin(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/projectile/WitherSkull;onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V")
    private void mortem_onHitEntityBuff(EntityHitResult entityHitResult, CallbackInfo ci){
        Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            if (entity instanceof LivingEntity) {
                if (entity2 instanceof WitherBoss witherBoss && witherBoss.isPowered()) {
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 240, 4), this.getEffectSource());
                }
        }
    }

    @Inject(at = @At("HEAD"), method = "onHit(Lnet/minecraft/world/phys/HitResult;)V")
    private void mortem_onHitExplosionBuff(HitResult hitResult, CallbackInfo ci){
        Entity entity2 = this.getOwner();
        if (!this.level().isClientSide()) {
            if (entity2 instanceof WitherBoss witherBoss && witherBoss.isPowered()) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, Level.ExplosionInteraction.MOB);
            }
            this.discard();
        }
    }
 }
