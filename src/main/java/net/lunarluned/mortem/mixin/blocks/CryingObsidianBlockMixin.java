package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CryingObsidianBlock;
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
public class CryingObsidianBlockMixin {

    @Inject(method = "onPlace", at = @At("TAIL"))
    private void mortem_toObsidianOnPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved, CallbackInfo ci) {
        if (!(state.is(Blocks.CRYING_OBSIDIAN))) return;

        convertBlockToObsidian(level, pos);
    }

    @Inject(method = "neighborChanged", at = @At("HEAD"))
    private void mortem_hardenCryingObsidian(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, CallbackInfo ci) {

        if (!(state.is(Blocks.CRYING_OBSIDIAN))) return;

        convertBlockToObsidian(level, pos);
    }

    @Unique
    public void convertBlockToObsidian(Level level, BlockPos pos) {
        boolean touchingWater = false;
        boolean touchingLava = false;

        for (Direction dir : Direction.values()) {
            FluidState fluid = level.getFluidState(pos.relative(dir));

            if (fluid.is(FluidTags.WATER)) {
                touchingWater = true;
            }

            if (fluid.is(FluidTags.LAVA)) {
                touchingLava = true;
            }

            if (touchingWater && touchingLava) {
                level.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.levelEvent(2010, pos, net.minecraft.world.level.block.Block.getId(level.getBlockState(pos)));
                    serverLevel.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1F, 0.1F);
                }
                return;
            }
        }
    }
}
