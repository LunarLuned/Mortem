package net.lunarluned.mortem.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class SiftingTableBlock extends Block {
    public static final EnumProperty<Direction> FACING;
    public static final MapCodec<SiftingTableBlock> CODEC = simpleCodec(SiftingTableBlock::new);
    public static final IntegerProperty WATER = IntegerProperty.create("water", 0, 16);
    public static final int MAX_WATER = 16;

    public SiftingTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
        .setValue(WATER, 0));
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                                   Player player, InteractionHand hand, BlockHitResult hit) {
        int water = state.getValue(WATER);

        if (stack.is(Items.BUCKET)) {
            if (water < MAX_WATER) return InteractionResult.TRY_WITH_EMPTY_HAND;
            fill(level, pos, state, player, hand, stack, 0,
                    new ItemStack(Items.WATER_BUCKET), SoundEvents.BUCKET_FILL, GameEvent.FLUID_PICKUP);
            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.GLASS_BOTTLE)) {
            if (water < 8) return InteractionResult.TRY_WITH_EMPTY_HAND;
            fill(level, pos, state, player, hand, stack, water - 8,
                    PotionContents.createItemStack(Items.POTION, Potions.WATER), SoundEvents.BOTTLE_FILL, GameEvent.FLUID_PICKUP);
            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.WATER_BUCKET)) {
            if (water >= MAX_WATER) return InteractionResult.TRY_WITH_EMPTY_HAND;
            fill(level, pos, state, player, hand, stack, MAX_WATER, new ItemStack(Items.BUCKET), SoundEvents.BUCKET_EMPTY, GameEvent.FLUID_PLACE);
            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.POTION) && stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER)) {
            if (water >= MAX_WATER) return InteractionResult.TRY_WITH_EMPTY_HAND;
            fill(level, pos, state, player, hand, stack, Math.min(MAX_WATER, water + 8),
                    new ItemStack(Items.GLASS_BOTTLE), SoundEvents.BOTTLE_EMPTY, GameEvent.FLUID_PLACE);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    private static void fill(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack stack, int newWater, ItemStack result, SoundEvent sound, Holder<GameEvent> event) {
        if (level.isClientSide()) return;
        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, result));
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        level.setBlock(pos, state.setValue(WATER, newWater), Block.UPDATE_ALL);
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, event, pos);
    }

    public @NonNull MapCodec<SiftingTableBlock> codec() {
        return CODEC;
    }


    protected @NonNull BlockState rotate(final BlockState state, final Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected @NonNull BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATER);
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

    public static void consumeWater(Level level, BlockPos pos, BlockState state) {
        int water = state.getValue(WATER);
        if (water <= 0) return;
        level.setBlock(pos, state.setValue(WATER, water - 1), Block.UPDATE_ALL);
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
