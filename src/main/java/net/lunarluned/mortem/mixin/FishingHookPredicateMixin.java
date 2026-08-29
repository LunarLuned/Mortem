package net.lunarluned.mortem.mixin;

import net.minecraft.advancements.predicates.entity.FishingHookPredicate;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHookPredicate.class)
public class FishingHookPredicateMixin {

    // Allows us to test the fishing loot in creative; as new versions do not let us get rare loot from fishing unless it's a large body of water.

    @Inject(
            method = "matches",
            at = @At("HEAD"),
            cancellable = true)
    private void mortem_overrideFishingCreativePredicate(Entity entity, ServerLevel level, Vec3 position, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) entity;

            if (player.isCreative() && Commands.LEVEL_GAMEMASTERS.check(player.permissions())) {
                cir.setReturnValue(true);
            }
        }
    }
}