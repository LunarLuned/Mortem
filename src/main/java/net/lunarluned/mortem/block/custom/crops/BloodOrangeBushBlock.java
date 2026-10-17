package net.lunarluned.mortem.block.custom.crops;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public class BloodOrangeBushBlock extends SweetBerryBushBlock {

    public BloodOrangeBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return new ItemStack(ModItems.BLOOD_ORANGE);
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        int age = state.getValue(AGE);
        if(age > 1) {
            if (!(level instanceof ServerLevel serverLevel)) {
                return super.getDestroyProgress(state, player, level, pos);
            }

            player.hurtServer(serverLevel, player.damageSources().cactus(), 1.0F);
        }

        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
        int age = state.getValue(AGE);
        boolean isGrown = age == 3;
        if(age > 1) {
            if (!(level instanceof ServerLevel serverLevel)) {
                return InteractionResult.SUCCESS;
            }

            player.hurtServer(serverLevel, player.damageSources().cactus(), 2.0F);
            int count = 1 + level.getRandom().nextInt(2);
            popResource(level, pos, new ItemStack(ModItems.BLOOD_ORANGE, count + (isGrown ? 1 : 0)));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                    1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            BlockState newState = state.setValue(AGE, 1);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos) {
        return state.is(BlockTags.NETHER_CARVER_REPLACEABLES);
    }

    @Override
    protected void randomTick(@NonNull BlockState state, ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (level.dimension() != Level.NETHER) {
            level.destroyBlock(pos, true);
            return;
        }
        int age = state.getValue(AGE);
        if (age < MAX_AGE && random.nextInt(10) == 0) {
            BlockState newState = state.setValue(AGE, age + 1);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
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
