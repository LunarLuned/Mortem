package net.lunarluned.mortem.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lunarluned.mortem.MortemTags;
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
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiplayerToolBreakMixin {

    // If item being used to mine is at low durability, cancel the mine.

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void mortem_stopBreaking(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            cir.cancel();
            if (!heldItem.is(MortemTags.METAL_ITEMS)) {
                player.playSound(SoundEvents.WOOD_BREAK);
            } else
                player.playSound(SoundEvents.METAL_BREAK);
        }
    }

    // If trying to attack with an item, cancel the attack and play a sound.

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void mortem_stopAttacking(Player player, Entity entity, CallbackInfo ci) {
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            ci.cancel();
            if (!heldItem.is(MortemTags.METAL_ITEMS)) {
                player.playSound(SoundEvents.WOOD_BREAK);
            } else
                player.playSound(SoundEvents.METAL_BREAK);
        }
    }

    // these two were added simply because it wasnt working fully and i basically just wanted to make sure. This Might not Even Work!
    @Inject(method = "performUseItemOn", at = @At("HEAD"), cancellable = true)
    private void mortem_performUseItemOn(LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (localPlayer == null) return;

        ItemStack heldItem = localPlayer.getMainHandItem();

        cancelBreak(heldItem, localPlayer, cir);
    }
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void mortem_useItemOn(LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (localPlayer == null) return;

        ItemStack heldItem = localPlayer.getMainHandItem();

        cancelBreak(heldItem, localPlayer, cir);
    }

    // If item being used is still being used while it's at that durabilty, cancel the use and play a sound.

    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void mortem_useItem(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        cancelBreak(heldItem, player, cir);
    }

    @Unique
    private boolean toolDurabilityLow(ItemStack itemStack) {
        return (itemStack.getItem() instanceof Item) && itemStack.isEnchanted()
                && (itemStack.getMaxDamage() - itemStack.getDamageValue() <= 3);
    }
    @Unique
    private void cancelBreak(ItemStack heldItem, Player player, CallbackInfoReturnable<InteractionResult> cir) {
        if (toolDurabilityLow(heldItem) && heldItem.isBarVisible()) {
            cir.cancel();
            if (!heldItem.is(MortemTags.METAL_ITEMS)) {
                player.playSound(SoundEvents.WOOD_BREAK);
            } else
                player.playSound(SoundEvents.METAL_BREAK);
        }
    }
}