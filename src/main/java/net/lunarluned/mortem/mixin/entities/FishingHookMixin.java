package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.item.custom.MortemFishingRodItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Entity {

    public FishingHookMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    @Nullable
    public abstract Player getPlayerOwner();

    @Shadow
    private int timeUntilLured;

    @Inject(method = "catchingFish", at = @At("TAIL"))
    private void applyRodSpeed(BlockPos pos, CallbackInfo ci) {
        Player player = getPlayerOwner();
        if (player == null) return;

        ItemStack rod = player.getMainHandItem();
        if (!(rod.getItem() instanceof MortemFishingRodItem fishingRod)) return;

        float speed = fishingRod.getSpeedMultiplier();

        if (speed > 1.0F && timeUntilLured > 1) {
            int bonus = Math.max(1, (int)(speed - 1.0F));
            timeUntilLured = Math.max(1, timeUntilLured - bonus);
        }
    }

    @Inject(
            method = "shouldStopFishing",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeIfInvalid(Player owner, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = owner.getMainHandItem();
        ItemStack offHandStack = owner.getOffhandItem();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof MortemFishingRodItem;
        boolean offHandHasRod = offHandStack.getItem() instanceof MortemFishingRodItem;

        if (!owner.isRemoved() && owner.isAlive() && (mainHandHasRod || offHandHasRod) && this.distanceToSqr(owner) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }
}