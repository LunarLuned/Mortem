package net.lunarluned.mortem.block.custom.crops;

import net.lunarluned.mortem.Mortem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class NetherCropBlock extends CropBlock {
    public NetherCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos) {
        return state.is(BlockTags.SUPPORTS_NETHER_WART);
    }

    @Override
    protected void randomTick(@NonNull BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.dimension() != Level.NETHER) {
            level.destroyBlock(pos, true);
            return;
        }
        int age = this.getAge(state);
        if (age < this.getMaxAge() && random.nextInt(10) == 0) {
            level.setBlock(pos, this.getStateForAge(age + 1), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        if (Mortem.IS_ENIGMA_INSTALLED) {
            return false;
        } else
            return super.isBonemealSuccess(level, random, pos, state);
    }

    // Only Rotten Bone Meal works if Enigma is installed
    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        if (Mortem.IS_ENIGMA_INSTALLED) {
            return false;
        } else
            return super.isValidBonemealTarget(level, pos, state);
    }
}
