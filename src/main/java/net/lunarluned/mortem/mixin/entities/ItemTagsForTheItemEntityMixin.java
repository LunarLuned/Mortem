package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.MortemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemTagsForTheItemEntityMixin {

    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void mortem_fireproofItems(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir)
    {
        ItemStack stack = getItem();

        if (stack.is(MortemTags.FIREPROOF_ITEMS) && source.is(DamageTypeTags.IS_FIRE)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mortem_applyCustomItemTags(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity) (Object) this;
        ItemStack stack = getItem();

        if (stack.is(MortemTags.FLOATING_ITEMS)) {
            Vec3 velocity = entity.getDeltaMovement();

            if (velocity.y < 0.02D) {
                entity.setDeltaMovement(
                        velocity.x,
                        0.05D,
                        velocity.z
                );
            }
        }
    }
}