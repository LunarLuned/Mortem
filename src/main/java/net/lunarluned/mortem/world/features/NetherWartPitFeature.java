package net.lunarluned.mortem.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NetherWartPitFeature extends Feature<NoneFeatureConfiguration> {
    public NetherWartPitFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();

        BlockPos.MutableBlockPos pos = ctx.origin().mutable();
        int limit = 32;
        while (limit-- > 0 && pos.getY() > level.getMinY() + 8) {
            if (level.isEmptyBlock(pos) && level.getBlockState(pos.below()).is(Blocks.NETHERRACK)) break;
            pos.move(Direction.DOWN);
        }
        if (limit <= 0 || !level.isEmptyBlock(pos.above(2))) return false;

        BlockPos ground = pos.below();
        int r = 2 + random.nextInt(2);
        int depth = 2 + random.nextInt(5);
        int floorY = ground.getY() - depth;

        float inner = r + 0.5f;
        float outer = r + 5.5f;
        int bound = r + 1;

        for (int dx = -bound; dx <= bound; dx++)
            for (int dz = -bound; dz <= bound; dz++) {
                if (dx * dx + dz * dz > outer * outer) continue;
                for (int y = floorY; y <= ground.getY(); y++) {
                    BlockState s = level.getBlockState(new BlockPos(ground.getX() + dx, y, ground.getZ() + dz));
                    if (s.isAir() || !s.getFluidState().isEmpty()) return false;
                }
            }

        for (int dx = -bound; dx <= bound; dx++) {
            for (int dz = -bound; dz <= bound; dz++) {
                int d2 = dx * dx + dz * dz;
                if (d2 > outer * outer) continue;
                boolean interior = d2 <= inner * inner;

                for (int y = floorY + 1; y <= ground.getY(); y++) {
                    BlockPos p = new BlockPos(ground.getX() + dx, y, ground.getZ() + dz);
                    if (interior) {
                        level.setBlock(p, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                    } else if (y < ground.getY() && random.nextFloat() < 0.15f) {
                        level.setBlock(p, Blocks.NETHER_WART_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }

        for (int dx = -bound; dx <= bound; dx++)
            for (int dz = -bound; dz <= bound; dz++)
                if (dx * dx + dz * dz <= inner * inner)
                    placeFloor(level, random, new BlockPos(ground.getX() + dx, floorY, ground.getZ() + dz));

        return true;
    }

    private void placeFloor(WorldGenLevel level, RandomSource random, BlockPos p) {
        float roll = random.nextFloat();
        if (roll < 0.55f) {
            level.setBlock(p, Blocks.SOUL_SAND.defaultBlockState(), Block.UPDATE_CLIENTS);
            if (random.nextFloat() < 0.7f) {
                level.setBlock(p.above(), Blocks.NETHER_WART.defaultBlockState()
                        .setValue(NetherWartBlock.AGE, random.nextInt(4)), Block.UPDATE_CLIENTS);
            }
        } else if (roll < 0.67f) {
            level.setBlock(p.above(), Blocks.NETHER_WART_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}