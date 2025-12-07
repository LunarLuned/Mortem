package net.lunarluned.mortem.mixin.blocks;

import com.mojang.serialization.MapCodec;
import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilMixin extends Block {
    @Shadow @Final public static EnumProperty<Direction> FACING;

    public AnvilMixin(Properties properties) {
        super(properties);
    }


    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionHand hand = player.getUsedItemHand();
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.IRON_INGOT) && !blockState.is(Blocks.ANVIL)) {
            if (!level.isClientSide()) {
                if (blockState.is(Blocks.CHIPPED_ANVIL)) {
                    level.setBlockAndUpdate(blockPos, Blocks.ANVIL.defaultBlockState().setValue(FACING, (Direction)blockState.getValue(FACING)));
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    level.playSound(null, blockPos, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        stack.consume(1, player);
                    }
                } else if (blockState.is(Blocks.DAMAGED_ANVIL)) {
                    level.setBlockAndUpdate(blockPos, Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(FACING, (Direction)blockState.getValue(FACING)));
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    level.playSound(null, blockPos, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        stack.consume(1, player);
                    }
                } else {
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
