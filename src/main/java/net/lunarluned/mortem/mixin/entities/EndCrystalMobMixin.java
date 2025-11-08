package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMobMixin extends Entity {
    protected EndCrystalMobMixin(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
    public void mortem_breakCrystalWithPickaxe(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.getEntity() instanceof Player) {

            ItemStack mainHand = ((Player)damageSource.getEntity()).getMainHandItem();

            if (!((Player)damageSource.getEntity()).isCreative()) {
                if (mainHand.is(ItemTags.PICKAXES)) {
                    DamageSource damageSource2 = damageSource.getEntity() != null ? this.damageSources().explosion(this, damageSource.getEntity()) : null;
                    serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1, Level.ExplosionInteraction.MOB);
                } else {
                    cir.setReturnValue(false);
                }
            } else {
                DamageSource damageSource2 = damageSource.getEntity() != null ? this.damageSources().explosion(this, damageSource.getEntity()) : null;
                serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1, Level.ExplosionInteraction.MOB);
            }
        }
    }
}
