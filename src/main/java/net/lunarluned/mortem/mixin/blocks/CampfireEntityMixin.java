package net.lunarluned.mortem.mixin.blocks;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireEntityMixin extends BlockEntity {

    public CampfireEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Inject(method = "cookTick", at = @At("HEAD"))
    private static void mortem_animateTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck, CallbackInfo ci) {
        if (!serverLevel.isClientSide() && !(serverLevel.dimension() == Level.NETHER)) {

            int randomValue = Mth.nextInt(RandomSource.create(), 1, 100);

            if (blockState.getValue(CampfireBlock.LIT)) {
                if (serverLevel.isRainingAt(blockPos.above()) && randomValue < 20) {
                    CampfireBlock.dowse(null, serverLevel, blockPos, blockState);
                    serverLevel.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    serverLevel.setBlock(blockPos, blockState.setValue(CampfireBlock.LIT, false), 3);
                }
            }

            int m;
            int l;
            int k = blockPos.getX();
            int j = 4;

            // Scans the area for nearby players

            AABB aABB = new AABB(k, l = blockPos.getY(), m = blockPos.getZ(), k + 1, l + 1, m + 1).inflate(j).expandTowards(0.0, serverLevel.getHeight(), 0.0);
            List<Player> nearbyEntities = serverLevel.getEntitiesOfClass(Player.class, aABB);

            for (Player player : nearbyEntities) {
                if (!player.hasEffect(MobEffects.REGENERATION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, true, true));
                }
            }
        }
    }
    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void onTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck, CallbackInfo ci) {

        RandomSource random = serverLevel.getRandom();
        if (random.nextInt(100) >= 5) return;

        // Try to ignite one nearby position
        attemptUnderneathBlockSpread(serverLevel, blockPos, random);
    }

    @Unique
    private static void attemptUnderneathBlockSpread(ServerLevel level, BlockPos blockPos, RandomSource random) {
        int dx = random.nextInt(5) - 3;
        int dz = random.nextInt(5) - 3;
        int dy = random.nextInt(5) - 3;

        BlockPos target = blockPos.offset(dx, dy, dz);

        if (!level.getBlockState(target).isAir()) return;

        BlockPos below = target.below();
        BlockState belowState = level.getBlockState(below);
        Block belowBlock = belowState.getBlock();

        BlockPos above = target.multiply(2);
        BlockState aboveState = level.getBlockState(above);
        Block aboveBlock = aboveState.getBlock();

        try {
            FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();
            if (registry.get(belowBlock) != null && belowState.is(MortemTags.FLAMMABLE_BLOCKS)) {
                level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
            }
            if (registry.get(aboveBlock) != null && aboveState.is(MortemTags.FLAMMABLE_BLOCKS)) {
                level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
/*


 */
    }


}
