package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.MortemTags;
import net.lunarluned.mortem.util.CampfireBurnTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

    // light campfire initally
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void mortem_campfireLightFromTorch(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if (!state.getValue(CampfireBlock.LIT)) {
            if (stack.is(MortemTags.TORCHES)) {
                if (!level.isClientSide()) {
                    // set campfire lit
                    if (state.hasProperty(CampfireBlock.LIT) && !state.getValue(CampfireBlock.LIT)) {
                        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.setBlock(pos, state.setValue(CampfireBlock.LIT, true), 3);

                        if (!player.isCreative() && player.getRandom().nextInt(10) >= 5) {
                            stack.shrink(1);
                            player.addItem(new ItemStack(Items.STICK));
                        }
                    }
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (stack.is(Items.STICK)) {
                if (!level.isClientSide()) {
                    if (player.getRandom().nextInt(6) >= 5) {
                        // set campfire lit
                        if (state.hasProperty(CampfireBlock.LIT) && !state.getValue(CampfireBlock.LIT)) {
                            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            level.setBlock(pos, state.setValue(CampfireBlock.LIT, true), 3);
                        }
                    }
                }
                level.playSound(null, pos, SoundEvents.WOOD_STEP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                player.causeFoodExhaustion(2);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }

    // fuel campfire
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void mortem_addFuel(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (!Mortem.IS_ENIGMA_INSTALLED || !state.getValue(CampfireBlock.LIT)) return;
        int fuel = mortem_fuelValue(itemStack);

        if (fuel <= 0) return;

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof CampfireBlockEntity be) {
            CampfireBurnTracker tracker = (CampfireBurnTracker) be;
            if (tracker.mortem_getBurnTicks() <= 100) {
                return;
            }
            tracker.mortem_setBurnTicks(Math.max(0, tracker.mortem_getBurnTicks() - fuel));
            be.setChanged();

            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.consume(1, player);
            }
            level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        player.causeFoodExhaustion(1);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    @Unique
    private static int mortem_fuelValue(ItemStack stack) {
        if (stack.is(ItemTags.COALS)) return 1000;
        if (stack.is(ItemTags.LOGS_THAT_BURN)) return 600;
        if (stack.is(ItemTags.PLANKS)) return 400;
        if (stack.is(Items.STICK)) return 100;
        return 0;
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue()
                    .setValue(CampfireBlock.LIT, false)
            );
        }
    }

    @Inject(method = "entityInside", at = @At("TAIL"))
    private void mortem_setFireEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise, CallbackInfo ci) {
        if (state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT) && !(entity instanceof ItemEntity)) {
            effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
            effectApplier.apply(InsideBlockEffectType.FIRE_IGNITE);
            effectApplier.runAfter(InsideBlockEffectType.FIRE_IGNITE, (entityx) -> entityx.hurt(entityx.level().damageSources().inFire(), 1));
        }
    }
    }
