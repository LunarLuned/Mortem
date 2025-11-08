package net.lunarluned.mortem.mixin.entities;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public abstract class FireworkRocketCooldownMixin extends Item {


    public FireworkRocketCooldownMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("HEAD"), method = "use")
    private void mortem_fireworkCooldown(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {

        if (player.isFallFlying() && !level.isClientSide()) {
            ItemStack itemStack = player.getItemInHand(interactionHand);

            player.getCooldowns().addCooldown(itemStack, 20);
        }
    }
}