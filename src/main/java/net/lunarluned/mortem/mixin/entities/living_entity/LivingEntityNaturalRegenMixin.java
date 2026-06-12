package net.lunarluned.mortem.mixin.entities.living_entity;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
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

    @Unique public int regenerationChance = 15;

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void mortem_redirectHealForNaturalRegen(float amount, CallbackInfo ci) {
        if (this.asLivingEntity() instanceof Player player) {
                if (player.hasEffect(ModEffects.STAGNATED)) {
                    if (player.getEffect(ModEffects.STAGNATED) != null) {
                        switch ((Objects.requireNonNull(player.getEffect(ModEffects.STAGNATED))).getAmplifier()) {
                            case 0:
                                regenerationChance = 7;
                                break;
                            case 1:
                                regenerationChance = 5;
                                break;
                            case 2:
                                regenerationChance = 3;
                                break;
                            case 3:
                                regenerationChance = 1;
                                break;
                            case 4:
                                regenerationChance = 0;
                                break;
                        }
                    }
            } if (player.isOnFire()) {
                    regenerationChance = regenerationChance / 2;
            }
                // Server level and gamerule check
                ServerLevel level = player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null;
                if (level != null && player.getHealth() < player.getMaxHealth() && !player.isSpectator() && player.getFoodData().getFoodLevel() <= 10 && !player.isCreative())
                    if (player.getFoodData().getFoodLevel() <= 10 || player.hasEffect(MobEffects.INSTANT_HEALTH) || player.getEffect(MobEffects.REGENERATION).getAmplifier() > 0)
                    if (!(regenerationChance == 0) && player.tickCount % 100 == 0) {
                    float g = player.getHealth();
                    int randomValue = Mth.nextInt(RandomSource.create(), 1, 100);
                    if (randomValue < regenerationChance || player.hasEffect(MobEffects.INSTANT_HEALTH) || player.getEffect(MobEffects.REGENERATION).getAmplifier() > 0) {
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
            }
        }
    }