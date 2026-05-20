package net.lunarluned.mortem.misc;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class FungalInfectSpreadHandler {

    private static final double SPREAD_RADIUS = 6.0;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                MobEffectInstance effect = player.getEffect(ModEffects.FUNGALLY_INFECTED);
                if (effect == null) continue;

                ServerLevel level = player.level();

                List<ServerPlayer> nearby = level.getEntitiesOfClass(
                        ServerPlayer.class,
                        player.getBoundingBox().inflate(SPREAD_RADIUS),
                        p -> p != player && !p.hasEffect(ModEffects.FUNGALLY_INFECTED)
                );

                for (ServerPlayer target : nearby) {

                    if (level.getRandom().nextFloat() < 0.25f) {

                        target.addEffect(new MobEffectInstance(
                                ModEffects.FUNGALLY_INFECTED,
                                effect.getDuration(),
                                effect.getAmplifier(),
                                true,
                                true,
                                true
                        ));
                    }
                }
            }
        });
    }
}