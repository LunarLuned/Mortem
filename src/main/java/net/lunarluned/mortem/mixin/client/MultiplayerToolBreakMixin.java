package net.lunarluned.mortem.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiplayerToolBreakMixin {

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void mortem_stopBreaking(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem)) {
            cir.setReturnValue(InteractionResult.FAIL.consumesAction());
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void mortem_stopAttacking(Player player, Entity entity, CallbackInfo ci) {
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();

        if (toolDurabilityLow(heldItem)) {
            ci.cancel();
            player.playSound(SoundEvents.WOOD_BREAK);
        }
    }

    @Unique
    private boolean toolDurabilityLow(ItemStack itemStack) {
        return (itemStack.getItem() instanceof Item)
                && (itemStack.getMaxDamage() - itemStack.getDamageValue() <= 3);
    }
}