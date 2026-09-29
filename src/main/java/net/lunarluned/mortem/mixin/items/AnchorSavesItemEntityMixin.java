package net.lunarluned.mortem.mixin.items;

import net.lunarluned.mortem.util.AnchorTracker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class AnchorSavesItemEntityMixin {
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void mortem_anchorFireProtection(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (AnchorTracker.isProtected(level, ((ItemEntity) (Object) this).position()) && source.is(DamageTypeTags.IS_FIRE)) {
            cir.setReturnValue(false);
        }
    }
}