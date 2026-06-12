package net.lunarluned.mortem.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin {

    @Redirect(
            method = "getLocalizedDeathMessage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getDisplayName()Lnet/minecraft/network/chat/Component;"
            )
    )
    private Component mortem_redactInvisibleNames(Entity entity) {
        if (entity instanceof Player player
                && player.hasEffect(MobEffects.INVISIBILITY)) {
            return Component.literal("Somebody").withStyle(ChatFormatting.OBFUSCATED);
        }

        return entity.getDisplayName();
    }
}