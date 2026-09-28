package net.lunarluned.mortem.mixin.blocks;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireEntityMixin extends BlockEntity {

    public CampfireEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    @Unique private int mortem$burnTicks = 0;
    @Unique private static final int MAX_BURN_TICKS = 36000;

    @Inject(method = "cookTick", at = @At("HEAD"))
    private static void mortem_animateTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck, CallbackInfo ci) {
        CampfireEntityMixin self = (CampfireEntityMixin) (Object) campfireBlockEntity;

        // Rain extinguishing campfire
        if (serverLevel.isRainingAt(blockPos.above()) && serverLevel.getRandom().nextInt(100) < 20) {
            mortem$extinguish(serverLevel, blockPos, blockState);
            self.mortem$burnTicks = 0;
            campfireBlockEntity.setChanged();
            return;
        }

        // Burnout over time
        if (Mortem.IS_ENIGMA_INSTALLED) {
            self.mortem$burnTicks++;
            if (self.mortem$burnTicks >= MAX_BURN_TICKS) {
                mortem$extinguish(serverLevel, blockPos, blockState);
                self.mortem$burnTicks = 0;
                campfireBlockEntity.setChanged();
                return;
            }
            if (self.mortem$burnTicks % 100 == 0) campfireBlockEntity.setChanged();
        }

        // Regen for nearby players, once per second
        if (serverLevel.getGameTime() % 20 == 0) {
            AABB area = new AABB(blockPos).inflate(4).expandTowards(0.0, serverLevel.getHeight(), 0.0);
            for (Player player : serverLevel.getEntitiesOfClass(Player.class, area)) {
                if (!player.hasEffect(MobEffects.REGENERATION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, true, true));
                }
            }
        }
    }

    @Unique
    private static void mortem$extinguish(ServerLevel level, BlockPos pos, BlockState state) {
        CampfireBlock.dowse(null, level, pos, state);
        level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.setBlock(pos, state.setValue(CampfireBlock.LIT, false), 3);
    }
    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void onTick(ServerLevel level, BlockPos pos, BlockState state, CampfireBlockEntity entity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeCache, CallbackInfo ci) {

        RandomSource random = level.getRandom();
        if (random.nextInt(100) >= 5) return;

        // Try to ignite one nearby position
        attemptUnderneathBlockSpread(level, pos, random);
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
            registry.get(belowBlock);
            if (belowState.is(MortemTags.FLAMMABLE_BLOCKS)) {
                level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
            }
            registry.get(aboveBlock);
            if (aboveState.is(MortemTags.FLAMMABLE_BLOCKS)) {
                level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
/*


 */
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void mortem$saveBurnTicks(ValueOutput output, CallbackInfo ci) {
        output.putInt("mortem_burn_ticks", this.mortem$burnTicks);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void mortem$loadBurnTicks(ValueInput input, CallbackInfo ci) {
        this.mortem$burnTicks = input.getIntOr("mortem_burn_ticks", 0);
    }


}
