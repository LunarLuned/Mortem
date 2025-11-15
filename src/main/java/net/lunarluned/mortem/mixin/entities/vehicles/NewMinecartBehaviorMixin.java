package net.lunarluned.mortem.mixin.entities.vehicles;

import com.llamalad7.mixinextras.sugar.Local;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartBehavior;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NewMinecartBehavior.class)
    public abstract class NewMinecartBehaviorMixin extends MinecartBehavior {
    protected NewMinecartBehaviorMixin(AbstractMinecart abstractMinecart) {
        super(abstractMinecart);
    }

        @Redirect(
                method = "calculateBoostTrackSpeed",
                at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                        ordinal = 0
                ),
                require = 1
        )
        private boolean mortem_secondRedirectedPoweredRailCheck(BlockState state, Block block) {
            return state.is(block) || state.is(ModBlocks.COPPER_RAIL);
        }

        @Inject(
                method = "calculateBoostTrackSpeed",
                at = @At("TAIL"),
                cancellable = true)
        private void mortem_modifyVelocity(Vec3 vec3, BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Vec3> cir, @Local(argsOnly = true) Vec3 originalVelocity, @Local(argsOnly = true) BlockState railState
        ) {
            if (blockState.is(ModBlocks.COPPER_RAIL) && (Boolean)blockState.getValue(PoweredRailBlock.POWERED)) {
                if (vec3.length() > 0.01) {
                    cir.setReturnValue(vec3.normalize().scale(vec3.length() + 0.00001));
                } else {
                    Vec3 vec32 = this.minecart.getRedstoneDirection(blockPos);
                    cir.setReturnValue(vec32.lengthSqr() <= (double)0.0F ? vec3 : vec32.scale(vec3.length() + 0.00001));
                }
            } else {
                cir.setReturnValue(vec3);
            }
        }
    }
