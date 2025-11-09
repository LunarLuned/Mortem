package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityNaturalRegenMixin {

    @Shadow @Nullable public abstract LivingEntity asLivingEntity();

    @Unique public int regenerationChance = 30;

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void mortem_redirectHealForNaturalRegen(float amount, CallbackInfo ci) {
        if (this.asLivingEntity() instanceof Player player) {
                if (player.hasEffect(ModEffects.STAGNATED)) {
                    regenerationChance = 20;
                    switch (Objects.requireNonNull(player.getEffect(ModEffects.STAGNATED)).getAmplifier()) {
                        case 0:
                            regenerationChance = 20;
                            break;
                        case 1:
                            regenerationChance = 15;
                            break;
                        case 2:
                            regenerationChance = 10;
                            break;
                        case 3:
                            regenerationChance = 5;
                            break;
                        case 4:
                            regenerationChance = 0;
                            break;
                    }
            } if (player.isOnFire()) {
                    regenerationChance = regenerationChance / 2;
            }
                // Server level and gamerule check
                ServerLevel level = player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null;
                if (level != null
                        && level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                        // player must have enough hunger to regen
                        && player.getFoodData().getFoodLevel() >= 18
                        // must actually be damaged
                        && player.getHealth() < player.getMaxHealth()
                        && !player.isSpectator()
                        && !player.isCreative()
                ) if (!(regenerationChance == 0)) {
                    float g = player.getHealth();
                    int randomValue = Mth.nextInt(RandomSource.create(), 1, 100);
                    if (randomValue < regenerationChance) {
                        if (g > 0.0F) {
                            player.setHealth(g + amount);
                        }
                        ci.cancel();
                    } else {
                        ci.cancel();
                    }
                } else {
                    ci.cancel();
                }
            } else {
                return;
            }
        }
    }