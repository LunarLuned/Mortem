package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMobMixin extends Entity {
    @Shadow public abstract void kill(ServerLevel serverLevel);

    protected EndCrystalMobMixin(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
    public void mortem_breakCrystalWithPickaxe(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.getEntity() instanceof Player) {

            ItemStack mainHand = ((Player)damageSource.getEntity()).getMainHandItem();

            if (!((Player)damageSource.getEntity()).isCreative()) {
                if (mainHand.is(ItemTags.PICKAXES)) {
                    serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1.5f, Level.ExplosionInteraction.MOB);
                    kill(serverLevel);
                } else {
                    cir.setReturnValue(false);
                }
            } else {
                serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1.5f, Level.ExplosionInteraction.MOB);
                kill(serverLevel);
            }
            cir.setReturnValue(false);
        }
    }
}
