package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void mortem_campfireLightFromTorch(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.is(MortemTags.TORCHES)) {
            if (!level.isClientSide()) {
                // set campfire lit
                if (blockState.hasProperty(CampfireBlock.LIT) && !blockState.getValue(CampfireBlock.LIT)) {
                    level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(blockPos, blockState.setValue(CampfireBlock.LIT, true), 3);
                }
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue()
                    .setValue(CampfireBlock.LIT, false)
            );
        }
    }

    @Inject(method = "entityInside", at = @At("TAIL"))
    private void mortem_setFireEntityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, CallbackInfo ci) {
        if (blockState.hasProperty(CampfireBlock.LIT) && blockState.getValue(CampfireBlock.LIT)) {
            insideBlockEffectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
            insideBlockEffectApplier.apply(InsideBlockEffectType.FIRE_IGNITE);
            insideBlockEffectApplier.runAfter(InsideBlockEffectType.FIRE_IGNITE, (entityx) -> entityx.hurt(entityx.level().damageSources().inFire(), 1));
        }
    }
    }
