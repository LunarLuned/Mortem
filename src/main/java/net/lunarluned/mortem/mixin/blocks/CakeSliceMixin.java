package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public class CakeSliceMixin {

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private static void mortem_giveCakeSlice(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && player.isCrouching()) {

            if (!level.isClientSide()) {

                if (!player.isCreative()) {
                    player.addItem(new ItemStack(ModItems.CAKE_SLICE));
                }
                int bites = state.getValue(CakeBlock.BITES);
                if (bites < 6) {
                    level.setBlock(pos, state.setValue(CakeBlock.BITES, bites + 1), 3);
                } else {
                    level.removeBlock(pos, false);
                }
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}