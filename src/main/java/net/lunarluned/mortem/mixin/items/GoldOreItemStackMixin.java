package net.lunarluned.mortem.mixin.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class GoldOreItemStackMixin {

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void moenigma_isCopperCorrectToolForDrops(BlockState state, CallbackInfoReturnable<Boolean> cir) {

        if (state.is(Blocks.GOLD_BLOCK)
                || state.is(Blocks.GOLD_ORE)
                || state.is(Blocks.RAW_GOLD_BLOCK)
                || state.is(Blocks.DEEPSLATE_GOLD_ORE)
                || state.is(Blocks.NETHER_GOLD_ORE)) {

            ItemStack stack = (ItemStack)(Object)this;

            if (stack.is(Items.COPPER_PICKAXE) || stack.is(Items.GOLDEN_PICKAXE)) {
                stack.setDamageValue(5);
                cir.setReturnValue(true);
            }
        }
    }
}