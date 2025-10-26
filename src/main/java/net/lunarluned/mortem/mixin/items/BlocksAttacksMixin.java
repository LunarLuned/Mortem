package net.lunarluned.mortem.mixin.items;

import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlocksAttacks.class)
public abstract class BlocksAttacksMixin {

    @Inject(method = "blockDelayTicks", at = @At("RETURN"), cancellable = true)
    private void modifyBlockDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
        cir.cancel();
    }
}
