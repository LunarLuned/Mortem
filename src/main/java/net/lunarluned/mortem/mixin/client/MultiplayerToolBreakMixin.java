package net.lunarluned.mortem.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiplayerToolBreakMixin {

    @Shadow public abstract void releaseUsingItem(Player player);

    // If item being used to mine is at low durability, cancel the mine.

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void mortem_stopBreaking(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            cir.setReturnValue(InteractionResult.FAIL.consumesAction());
        }
    }

    // If trying to attack with an item, cancel the attack and play a sound.

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void mortem_stopAttacking(Player player, Entity entity, CallbackInfo ci) {
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            ci.cancel();
            player.playSound(SoundEvents.WOOD_BREAK);
        }
    }

    // If item being used is still being used while it's at that durabilty, cancel the use and play a sound.

    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void mortem_useItem(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            cir.cancel();
            releaseUsingItem(player);
            player.playSound(SoundEvents.WOOD_BREAK);
        }
    }

    @Unique
    private boolean toolDurabilityLow(ItemStack itemStack) {
        return (itemStack.getItem() instanceof Item)
                && (itemStack.getMaxDamage() - itemStack.getDamageValue() <= 3);
    }
}