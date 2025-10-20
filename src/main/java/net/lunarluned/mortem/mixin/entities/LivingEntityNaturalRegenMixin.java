package net.lunarluned.mortem.mixin.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityNaturalRegenMixin {

    @Shadow @Nullable public abstract LivingEntity asLivingEntity();

    @Shadow @Nullable protected EntityReference<Player> lastHurtByPlayer;

    @Inject(
            method = "heal",
            at = @At(
                    "HEAD"
            ),
            cancellable = true)
    private void mortem_redirectHealForNaturalRegen(float amount, CallbackInfo ci) {
        // Only adjust for server-side players
        if (this.asLivingEntity() instanceof Player player) {
            if (!player.hasEffect(MobEffects.REGENERATION)) {
                // Server level and gamerule check
                ServerLevel level = player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null;
                if (level != null
                        && level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                        // player must have hunger >= 18 (vanilla natural regen threshold)
                        && player.getFoodData().getFoodLevel() >= 18
                        // must actually be damaged
                        && player.getHealth() < player.getMaxHealth()
                        // ignore spectator & creative (vanilla doesn't regen in spectator; creative not needed)
                        && !player.isSpectator()
                        && !player.isCreative()
                ) {
                    float g = player.getHealth();
                    int randomValue = Mth.nextInt(RandomSource.create(), 1, 100);
                    if (randomValue < 30) {
                        if (g > 0.0F) {
                            player.setHealth(g + amount);
                        }
                        ci.cancel();
                    } else {
                        ci.cancel();
                    }
                }
            } else {
                return;
            }
        }
    }
}