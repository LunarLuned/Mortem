package net.lunarluned.mortem.mixin.entities.vehicles;

import com.llamalad7.mixinextras.sugar.Local;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.block.custom.CopperRailBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartMixin extends VehicleEntity {
    @Shadow public abstract boolean isRedstoneConductor(BlockPos blockPos);

    public AbstractMinecartMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "getRedstoneDirection",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            ),
            require = 1,
            cancellable = true,
            order = 100000
    )
    void getPushDirection(BlockPos blockPos, CallbackInfoReturnable<Vec3> cir, @Local(ordinal = 0) BlockState blockState) {
        if (blockState.is(ModBlocks.COPPER_RAIL) && blockState.getValue(PoweredRailBlock.POWERED)) {
            RailShape railShape = (RailShape)blockState.getValue(((BaseRailBlock)blockState.getBlock()).getShapeProperty());
            if (railShape == RailShape.EAST_WEST) {
                if (this.isRedstoneConductor(blockPos.west())) {
                    cir.setReturnValue(new Vec3((double)1.0F, (double)0.0F, (double)0.0F));
                }

                if (this.isRedstoneConductor(blockPos.east())) {
                    cir.setReturnValue(new Vec3((double)-1.0F, (double)0.0F, (double)0.0F));
                }
            }

            else if (railShape == RailShape.NORTH_SOUTH) {
                if (this.isRedstoneConductor(blockPos.north())) {
                    cir.setReturnValue(new Vec3((double)0.0F, (double)0.0F, (double)1.0F));
                }

                if (this.isRedstoneConductor(blockPos.south())) {
                    cir.setReturnValue(new Vec3((double)0.0F, (double)0.0F, (double)-1.0F));
                }
            }

            cir.setReturnValue(Vec3.ZERO);
        } else {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}
