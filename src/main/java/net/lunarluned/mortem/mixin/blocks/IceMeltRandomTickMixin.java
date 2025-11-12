package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class IceMeltRandomTickMixin {

    @Inject(method = "onPlace", at = @At("TAIL"))
    private void mortem_iceMeltOnPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved, CallbackInfo ci) {
        if (level.isClientSide()) return;

        if (level instanceof ServerLevel serverLevel) {

            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;

        if (state.is(Blocks.PACKED_ICE) || state.is(Blocks.BLUE_ICE) || state.is(Blocks.ICE)) {
            if (level.dimensionType().ultraWarm()) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
                level.levelEvent(2001, pos, Block.getId(state));
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z,
                        8, 0.25, 0.25, 0.25, 0.02f);
            }
        }
        }
    }
}