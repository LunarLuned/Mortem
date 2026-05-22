package net.lunarluned.mortem.mixin.items;

import net.lunarluned.mortem.MortemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ResinRepairItemStackMixin {

    @Inject(
            method = "isValidRepairItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void moenigma$resinRepairsMetal(
            ItemStack repairItem, CallbackInfoReturnable<Boolean> cir
    ) {

        ItemStack self = (ItemStack)(Object)this;

        if (!repairItem.is(Items.RESIN_CLUMP)) {
            return;
        }

        if (self.is(MortemTags.RESIN_REPAIRABLES)) {
            cir.setReturnValue(true);
        }
    }
}