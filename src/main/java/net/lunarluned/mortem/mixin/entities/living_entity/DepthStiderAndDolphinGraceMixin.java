package net.lunarluned.mortem.mixin.entities.living_entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Dolphin.class)
public abstract class DepthStiderAndDolphinGraceMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void mortem_preventDolphinsGraceWithDepthStrider(CallbackInfo ci) {

        Dolphin self = (Dolphin)(Object)this;

        List<Player> players = self.level().getEntitiesOfClass(
                Player.class,
                self.getBoundingBox().inflate(10.0D)
        );

        for (Player player : players) {

            int depthStrider = EnchantmentHelper.getEnchantmentLevel(
                    player.level().registryAccess()
                            .lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(Enchantments.DEPTH_STRIDER),
                    player
            );


            if (depthStrider > 0) {
                player.removeEffect(MobEffects.DOLPHINS_GRACE);
            }
        }
    }
}