package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CactusBlock.class)
public class CactusBlockMixin implements BonemealableBlock {

    @Shadow
    @Final
    public static IntegerProperty AGE;

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        CactusBlock block = (CactusBlock) (Object) this;
        while (serverLevel.getBlockState(blockPos).is(Blocks.CACTUS)) {
            blockPos = blockPos.above();
        }
        blockPos = blockPos.below();
        blockState = serverLevel.getBlockState(blockPos);
        if (serverLevel.isEmptyBlock(blockPos.above())) {
                    int j = blockState.getValue(AGE);
                    if (j == 15) {
                        serverLevel.setBlockAndUpdate(blockPos.above(), block.defaultBlockState());
                        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, 15));
                    } else {
                        serverLevel.setBlockAndUpdate(blockPos, (BlockState) blockState.setValue(AGE, Mth.clamp(j + Mth.nextInt(RandomSource.create(), 3, 5), 15, 15)));
                    }
        }
    }
}