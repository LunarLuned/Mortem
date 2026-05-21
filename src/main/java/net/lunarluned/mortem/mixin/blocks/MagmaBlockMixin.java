package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class MagmaBlockMixin {

    @Inject(method = "onPlace", at = @At("TAIL"))
    private void mortem_toNetherrackOnPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved, CallbackInfo ci) {
        if (!(state.is(Blocks.MAGMA_BLOCK))) return;

        convertBlockToNetherrack(level, pos);
    }

    @Inject(method = "neighborChanged", at = @At("HEAD"))
    private void mortem_softenMagmaBlock(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, CallbackInfo ci) {
        if (!(state.is(Blocks.MAGMA_BLOCK))) return;

        convertBlockToNetherrack(level, pos);
    }

    @Unique
    public void convertBlockToNetherrack(Level level, BlockPos pos) {
    boolean touchingWater = false;

        for (Direction dir : Direction.values()) {
            FluidState fluid = level.getFluidState(pos.relative(dir));

            if (fluid.is(FluidTags.WATER)) {
                touchingWater = true;
            }

            if (touchingWater) {
                level.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.levelEvent(2010, pos, net.minecraft.world.level.block.Block.getId(level.getBlockState(pos)));
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 8, 0.25, 0.25, 0.25, 0.02f);
                    serverLevel.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
                }
                return;
            }
        }
    }
}