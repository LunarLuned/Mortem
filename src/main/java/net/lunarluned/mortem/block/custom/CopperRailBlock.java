package net.lunarluned.mortem.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CopperRailBlock extends BaseRailBlock {
    public static final MapCodec<PoweredRailBlock> CODEC = simpleCodec(PoweredRailBlock::new);
    public static final EnumProperty<RailShape> SHAPE;
    public static final BooleanProperty POWERED;
    public static final EnumProperty<Direction> FACING;

    public CopperRailBlock(Properties properties) {
        super(false, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED, false));
    }

    public @NotNull MapCodec<PoweredRailBlock> codec() {
        return CODEC;
    }


    protected void updateState(BlockState blockState, Level level, BlockPos blockPos, Block block) {
        if (block.defaultBlockState().isSignalSource() && (new RailState(level, blockPos, blockState)).countPotentialConnections() == 3) {
            this.updateDir(level, blockPos, blockState, false);
        }
        boolean bl = (Boolean)blockState.getValue(POWERED);
        boolean bl2 = level.hasNeighborSignal(blockPos) || this.findPoweredRailSignal(level, blockPos, blockState, true, 0) || this.findPoweredRailSignal(level, blockPos, blockState, false, 0);
        if (bl2 != bl) {
            level.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, bl2), 3);
            level.updateNeighborsAt(blockPos.below(), this);
            if (((RailShape)blockState.getValue(SHAPE)).isSlope()) {
                level.updateNeighborsAt(blockPos.above(), this);
            }
        }

    }

    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        RailShape railShape = (RailShape)blockState.getValue(SHAPE);
        RailShape railShape2 = this.rotate(railShape, rotation);
        return (BlockState)blockState.setValue(SHAPE, railShape2);
    }

    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        RailShape railShape = (RailShape)blockState.getValue(SHAPE);
        RailShape railShape2 = this.mirror(railShape, mirror);
        return (BlockState)blockState.setValue(SHAPE, railShape2);
    }

    @Override
    public @NotNull Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE, POWERED, WATERLOGGED);
    }

    protected boolean findPoweredRailSignal(Level level, BlockPos blockPos, BlockState blockState, boolean bl, int i) {
        if (i >= 8) {
            return false;
        } else {
            int j = blockPos.getX();
            int k = blockPos.getY();
            int l = blockPos.getZ();
            boolean bl2 = true;
            RailShape railShape = (RailShape)blockState.getValue(SHAPE);
            switch (railShape) {
                case NORTH_SOUTH:
                    if (bl) {
                        ++l;
                    } else {
                        --l;
                    }
                    break;
                case EAST_WEST:
                    if (bl) {
                        --j;
                    } else {
                        ++j;
                    }
                    break;
                case ASCENDING_EAST:
                    if (bl) {
                        --j;
                    } else {
                        ++j;
                        ++k;
                        bl2 = false;
                    }

                    railShape = RailShape.EAST_WEST;
                    break;
                case ASCENDING_WEST:
                    if (bl) {
                        --j;
                        ++k;
                        bl2 = false;
                    } else {
                        ++j;
                    }

                    railShape = RailShape.EAST_WEST;
                    break;
                case ASCENDING_NORTH:
                    if (bl) {
                        ++l;
                    } else {
                        --l;
                        ++k;
                        bl2 = false;
                    }

                    railShape = RailShape.NORTH_SOUTH;
                    break;
                case ASCENDING_SOUTH:
                    if (bl) {
                        ++l;
                        ++k;
                        bl2 = false;
                    } else {
                        --l;
                    }
            }

            if (this.isSameRailWithPower(level, new BlockPos(j, k, l), bl, i, railShape)) {
                return true;
            } else {
                return bl2 && this.isSameRailWithPower(level, new BlockPos(j, k - 1, l), bl, i, railShape);
            }
        }
    }

    protected boolean isSameRailWithPower(Level level, BlockPos blockPos, boolean bl, int i, RailShape railShape) {
        BlockState blockState = level.getBlockState(blockPos);
        if (!blockState.is(this)) {
            return false;
        } else {
            RailShape railShape2 = (RailShape)blockState.getValue(SHAPE);
            if (railShape != RailShape.EAST_WEST || railShape2 != RailShape.NORTH_SOUTH && railShape2 != RailShape.ASCENDING_NORTH && railShape2 != RailShape.ASCENDING_SOUTH) {
                if (railShape != RailShape.NORTH_SOUTH || railShape2 != RailShape.EAST_WEST && railShape2 != RailShape.ASCENDING_EAST && railShape2 != RailShape.ASCENDING_WEST) {
                    if ((Boolean)blockState.getValue(POWERED)) {
                        return level.hasNeighborSignal(blockPos) ? true : this.findPoweredRailSignal(level, blockPos, blockState, bl, i + 1);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    static {
        SHAPE = BlockStateProperties.RAIL_SHAPE;
        FACING = BlockStateProperties.FACING;
        POWERED = BlockStateProperties.POWERED;
    }
}