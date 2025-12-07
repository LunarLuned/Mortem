package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.Block.pushEntitiesUp;

@Mixin(FarmBlock.class)
public abstract class FarmBlockFallMixin {

    @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
    private void mortem_fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, double d, CallbackInfo ci) {
        if (level.isClientSide()) return;

        if (entity.fallDistance > 2.0F) {
            level.setBlockAndUpdate(blockPos, pushEntitiesUp(blockState, Blocks.DIRT.defaultBlockState(), level, blockPos));
        }
        ci.cancel();
    }
}