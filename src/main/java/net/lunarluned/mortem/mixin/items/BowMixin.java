package net.lunarluned.mortem.mixin.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ProjectileWeaponItem.class)
public abstract class BowMixin {


    @Inject(at = @At("TAIL"), method = "shoot")
    private void mortem_flameReworkShoot(ServerLevel serverLevel, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack, List<ItemStack> list, float f, float g, boolean bl, LivingEntity livingEntity2, CallbackInfo ci) {
        ItemStack offHand = livingEntity.getItemInHand(InteractionHand.OFF_HAND);
        if (offHand.is(Items.FLINT_AND_STEEL)) {
            livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.MASTER, 1.0F, 1);
            livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.GENERIC_BURN, SoundSource.MASTER, 0.1F, 1);
        }
    }

        @Inject(at = @At("HEAD"), method = "createProjectile", cancellable = true)
    private void mortem_flameRework(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, CallbackInfoReturnable<Projectile> cir) {
        ItemStack offHand = livingEntity.getItemInHand(InteractionHand.OFF_HAND);

        if (offHand.is(Items.FLINT_AND_STEEL)) {

            offHand.hurtAndBreak(2, livingEntity, InteractionHand.OFF_HAND);

            Item var8 = itemStack2.getItem();
            ArrowItem var10000;
            if (var8 instanceof ArrowItem arrowItem) {
                var10000 = arrowItem;
            } else {
                var10000 = (ArrowItem)Items.ARROW;
            }

            ArrowItem arrowItem2 = var10000;
            AbstractArrow abstractArrow = arrowItem2.createArrow(level, itemStack2, livingEntity, itemStack);
            abstractArrow.setRemainingFireTicks(100);
            if (bl) {
                abstractArrow.setCritArrow(true);
            }

            cir.setReturnValue(abstractArrow);
        }
    }
}