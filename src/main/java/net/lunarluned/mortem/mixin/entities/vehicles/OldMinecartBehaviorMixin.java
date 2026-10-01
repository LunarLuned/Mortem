package net.lunarluned.mortem.mixin.entities.vehicles;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OldMinecartBehavior.class)
public abstract class OldMinecartBehaviorMixin extends MinecartBehavior {
    @Unique private boolean mortem_onPoweredCopper;

    protected OldMinecartBehaviorMixin(AbstractMinecart minecart) { super(minecart); }

    @Unique
    private boolean mortem_isPoweredCopper(Level level) {
        BlockState s = level.getBlockState(this.minecart.getCurrentBlockPosOrRailBelow());
        return s.is(ModBlocks.COPPER_RAIL) && s.getValue(PoweredRailBlock.POWERED);
    }

    @Inject(method = "moveAlongTrack", at = @At("HEAD"))
    private void mortem_checkRail(ServerLevel level, CallbackInfo ci) {
        mortem_onPoweredCopper = mortem_isPoweredCopper(level);
    }

    @Inject(method = "moveAlongTrack", at = @At("TAIL"))
    private void moenigma_copperBoost(ServerLevel level, CallbackInfo ci) {
        if (!mortem_onPoweredCopper) return;
        Vec3 vel = this.minecart.getDeltaMovement();
        double speed = vel.horizontalDistance();
        if (speed > 0.01) {
            this.minecart.setDeltaMovement(vel.add(vel.x / speed * 0.02, 0.0, vel.z / speed * 0.02));
        }
    }

    @ModifyReturnValue(method = "getMaxSpeed", at = @At("RETURN"))
    private double mortem_copperMaxSpeed(double original, ServerLevel level) {
        return mortem_isPoweredCopper(level) ? original * 2 : original;
    }
}