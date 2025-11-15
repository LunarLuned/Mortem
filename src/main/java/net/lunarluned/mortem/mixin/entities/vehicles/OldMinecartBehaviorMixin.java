package net.lunarluned.mortem.mixin.entities.vehicles;

import com.llamalad7.mixinextras.sugar.Local;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.block.custom.CopperRailBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.OldMinecartBehavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OldMinecartBehavior.class)
public class OldMinecartBehaviorMixin {
    @Redirect(
            method = "moveAlongTrack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                    ordinal = 0
            ),
            require = 1
    )
    private boolean redirectedPoweredRailCheck(BlockState blockState, Block block) {
        return blockState.is(Blocks.POWERED_RAIL) || blockState.is(ModBlocks.COPPER_RAIL);
    }

    @Inject(
            method = "moveAlongTrack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/OldMinecartBehavior;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;",
                    shift = At.Shift.BEFORE,
                    ordinal = 10
            ),
            cancellable = true,
            require = 1
    )
    private void setNewVelocity(ServerLevel world, CallbackInfo ci, @Local BlockState blockState) {
        if (!blockState.is(ModBlocks.COPPER_RAIL)) {
            return;
        }
        ci.cancel();

        OldMinecartBehavior This = (OldMinecartBehavior)(Object)this;
        Vec3 velocity = This.getDeltaMovement();

        double speed = velocity.horizontalDistance();
        if (speed > 0.01) {
            double correlation = velocity.dot(velocity.normalize());
            double factor = 0.06/speed;
            This.setDeltaMovement(
                    (correlation > 0.5)?
                            velocity.add(velocity.x*factor, 0.0, velocity.z*factor)
                            : velocity.subtract(velocity.x*factor, 0.0, velocity.z*factor)
            );
        } else {
            This.setDeltaMovement(velocity.scale(2));
        }
    }
}
