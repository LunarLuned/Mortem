package net.lunarluned.mortem.mixin.entities.vehicles;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NewMinecartBehavior.class)
public abstract class NewMinecartBehaviorMixin extends MinecartBehavior {

    protected NewMinecartBehaviorMixin(AbstractMinecart minecart) { super(minecart); }
    @ModifyReturnValue(method = "calculateBoostTrackSpeed", at = @At("RETURN"))
    private Vec3 mortem_copperBoost(Vec3 original, @Local(argsOnly = true) BlockState state) {
        if (state.is(ModBlocks.COPPER_RAIL) && state.getValue(PoweredRailBlock.POWERED)) {
            double speed = original.horizontalDistance();
            if (speed > 0.01) return original.normalize().scale(speed + 0.025);
        }
        return original;
    }

    @ModifyReturnValue(method = "getMaxSpeed", at = @At("RETURN"))
    private double mortem_copperMaxSpeed(double original) {
        BlockState s = this.minecart.level().getBlockState(this.minecart.getCurrentBlockPosOrRailBelow());
        if (s.is(ModBlocks.COPPER_RAIL) && s.getValue(PoweredRailBlock.POWERED)) {
            return original * 2.0;
        }
        return original;
    }
}