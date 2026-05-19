package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(StonecutterBlock.class)
public abstract class StonecutterBlockMixin extends Block {

    @Shadow
    @Final
    public static EnumProperty<Direction> FACING;

    @Unique
    private static final EnumProperty<AttachFace> FACE =
            BlockStateProperties.ATTACH_FACE;

    @Unique
    private static final VoxelShape FLOOR_SHAPE =
            Block.column(16.0F, 0.0F, 9.0F);

    @Unique
    private static final VoxelShape CEILING_SHAPE =
            Block.column(16.0F, 8.0F, 16.0F);

    @Unique
    private static final VoxelShape NORTH_SHAPE =
            Block.box(0, 0, 7, 16, 16, 16);

    @Unique
    private static final VoxelShape SOUTH_SHAPE =
            Block.box(0, 0, 0, 16, 16, 9);
    @Unique
    private static final VoxelShape WEST_SHAPE =
            Block.box(7, 0, 0, 16, 16, 16);

    @Unique
    private static final VoxelShape EAST_SHAPE =
            Block.box(0, 0, 0, 9, 16, 16);

    public StonecutterBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void mortem_init(BlockBehaviour.Properties properties, CallbackInfo ci) {

        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(FACE, AttachFace.FLOOR)
        );
    }

    @Inject(
            method = "createBlockStateDefinition",
            at = @At("TAIL")
    )
    private void mortem_addFaceProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(FACE);
    }

    /**
     * @author LunarLuned
     * @reason stonecutter multi-face placement
     */
    @Overwrite
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        for (Direction direction : context.getNearestLookingDirections()) {

            AttachFace face;
            Direction facing;

            if (direction.getAxis() == Direction.Axis.Y) {

                face = direction == Direction.UP
                        ? AttachFace.CEILING
                        : AttachFace.FLOOR;

                facing = context.getHorizontalDirection();

            } else {

                face = AttachFace.WALL;
                facing = direction.getOpposite();
            }

            BlockState state = ((StonecutterBlock)(Object)this)
                    .defaultBlockState()
                    .setValue(FACE, face)
                    .setValue(FACING, facing);

            if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
                return state;
            }
        }

        return null;
    }


    /**
     * @author LunarLuned
     * @reason stonecutter custom attachment hitboxes
     */
    @Overwrite
    protected @NonNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        AttachFace face = state.getValue(FACE);

        if (face == AttachFace.FLOOR) {
            return FLOOR_SHAPE;
        }

        if (face == AttachFace.CEILING) {
            return CEILING_SHAPE;
        }

        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> EAST_SHAPE;
        };
    }

    /**
     * @author LunarLuned
     * @reason stonecutter rotation support
     */
    @Overwrite
    public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    /**
     * @author LunarLuned
     * @reason stonecutter mirror support
     */
    @Overwrite
    public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}