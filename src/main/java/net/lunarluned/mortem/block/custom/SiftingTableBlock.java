package net.lunarluned.mortem.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class SiftingTableBlock extends Block {
    public static final EnumProperty<Direction> FACING;
    public static final MapCodec<SiftingTableBlock> CODEC = simpleCodec(SiftingTableBlock::new);

    public SiftingTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public MapCodec<SiftingTableBlock> codec() {
        return CODEC;
    }


    protected @NonNull BlockState rotate(final BlockState state, final Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected @NonNull BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }

    @Override
    public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    private static final VoxelShape SHAPE =
            Shapes.or(
                    Block.box(0, 0, 0, 16, 13, 16),
                    Block.box(0, 13, 13, 16, 14, 16),
                    Block.box(13, 13, 3, 16, 14, 13),
                    Block.box(0, 13, 3, 3, 14, 13),
                    Block.box(0, 13, 0, 16, 14, 3)
            );

}
