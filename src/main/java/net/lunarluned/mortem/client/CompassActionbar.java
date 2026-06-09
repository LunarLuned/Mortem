package net.lunarluned.mortem.client;


import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.lunarluned.mortem.Mortem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

    public class CompassActionbar {

        private static int tickCounter = 0;

        public static void register() {
            ClientTickEvents.END_CLIENT_TICK.register(client -> {

                if (client.player == null || client.level == null) return;

                if (!isHoldingCompass(client)) return;

                tickCounter++;

                if (tickCounter < 10) return;
                tickCounter = 0;

                BlockPos pos = client.player.getOnPos();
                Direction facing = client.player.getDirection();
                String facingFinal =
                        switch (facing) {
                            case WEST -> "West";
                            case EAST -> "East";
                            case NORTH -> "North";
                            case SOUTH -> "South";
                            default -> "???";
                        };

                String coords = "XYZ: " +
                        pos.getX() + ", " +
                        pos.getY() + ", " +
                        pos.getZ() + ", " +
                        "Facing: " + facingFinal;

                client.player.sendOverlayMessage(
                        Component.literal(coords)
                );
            });
        }

        private static boolean isHoldingCompass(Minecraft client) {
            assert client.player != null;
            return client.player.getMainHandItem().is(Items.COMPASS) || client.player.getOffhandItem().is(Items.COMPASS)
                    || client.player.getMainHandItem().is(Items.FILLED_MAP) || client.player.getOffhandItem().is(Items.FILLED_MAP);
        }
    }