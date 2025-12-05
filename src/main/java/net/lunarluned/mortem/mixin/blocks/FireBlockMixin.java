package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BaseFireBlock.class)
public abstract class FireBlockMixin {

    @Shadow protected abstract boolean canBurn(BlockState blockState);

    @Inject(method = "animateTick", at = @At("HEAD"))
    private void mortem_fastFireTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        for (int i = 0; i < 3; i++) {
            trySpread(level, blockPos, randomSource);
        }
    }

    @Unique
    private void trySpread(Level level, BlockPos blockPos, RandomSource randomSource) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState2 = level.getBlockState(blockPos2);
        if (!this.canBurn(blockState2) && !blockState2.isFaceSturdy(level, blockPos2, Direction.UP)) {
            if (this.canBurn(level.getBlockState(blockPos.west()))) {
                for(int i = 0; i < 2; ++i) {
                    double d = (double)blockPos.getX() + randomSource.nextDouble() * (double)0.1F;
                    double e = (double)blockPos.getY() + randomSource.nextDouble();
                    double f = (double)blockPos.getZ() + randomSource.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(blockPos.east()))) {
                for(int i = 0; i < 2; ++i) {
                    double d = (double)(blockPos.getX() + 1) - randomSource.nextDouble() * (double)0.1F;
                    double e = (double)blockPos.getY() + randomSource.nextDouble();
                    double f = (double)blockPos.getZ() + randomSource.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(blockPos.north()))) {
                for(int i = 0; i < 2; ++i) {
                    double d = (double)blockPos.getX() + randomSource.nextDouble();
                    double e = (double)blockPos.getY() + randomSource.nextDouble();
                    double f = (double)blockPos.getZ() + randomSource.nextDouble() * (double)0.1F;
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(blockPos.south()))) {
                for(int i = 0; i < 2; ++i) {
                    double d = (double)blockPos.getX() + randomSource.nextDouble();
                    double e = (double)blockPos.getY() + randomSource.nextDouble();
                    double f = (double)(blockPos.getZ() + 1) - randomSource.nextDouble() * (double)0.1F;
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(blockPos.above()))) {
                for(int i = 0; i < 2; ++i) {
                    double d = (double)blockPos.getX() + randomSource.nextDouble();
                    double e = (double)(blockPos.getY() + 1) - randomSource.nextDouble() * (double)0.1F;
                    double f = (double)blockPos.getZ() + randomSource.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double d = (double)blockPos.getX() + randomSource.nextDouble();
                double e = (double)blockPos.getY() + randomSource.nextDouble() * (double)0.5F + (double)0.5F;
                double f = (double)blockPos.getZ() + randomSource.nextDouble();
                level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }
    }
}