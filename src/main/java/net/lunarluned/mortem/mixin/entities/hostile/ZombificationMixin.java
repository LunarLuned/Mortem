package net.lunarluned.mortem.mixin.entities.hostile;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombificationMixin {

    @Inject(method = "doHurtTarget", at = @At("HEAD"))
    public void mortem_doHurtTarget(ServerLevel serverLevel, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player player) {
            int randomValue = Mth.nextInt(RandomSource.create(), 1, 10);
            int effectiveInfectChance = getInfectChance(player);
            if (randomValue < effectiveInfectChance) {
                if (!player.hasEffect(ModEffects.INFECTED) && !player.isBlocking()) {
                    if (player.hasEffect(ModEffects.IMMUNE)) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.HOSTILE, 1.0F, 3);
                    } else {
                        if (!player.level().isClientSide()) {
                            player.addEffect(new MobEffectInstance(ModEffects.INFECTED, 12000, 0));
                        }
                    }
                }
            }
        } else if (entity instanceof Villager villager) {
            if (!villager.hasEffect(ModEffects.INFECTED) || !villager.hasEffect(ModEffects.IMMUNE)) {
                if (!villager.level().isClientSide()) {
                    villager.addEffect(new MobEffectInstance(ModEffects.INFECTED, 12000, 0));
                }
            }

        }
    }

    @Unique
    private static int getInfectChance(Player player) {
        int armorValue = player.getArmorValue();

        return (int) Math.max(0.5f, 10.0f - (armorValue * 0.4f));
    }
}