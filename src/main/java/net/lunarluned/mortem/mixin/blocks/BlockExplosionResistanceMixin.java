package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.MortemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockExplosionResistanceMixin {

    @Inject(method = "getExplosionResistance", at = @At("HEAD"), cancellable = true)
    private void onGetExplosionResistance(CallbackInfoReturnable<Float> cir) {
        Block self = (Block) (Object) this;
        if (self.defaultBlockState().is(MortemTags.EXPLOSION_PROOF)) {
            cir.setReturnValue(12000.0F);
        }
        if (self.defaultBlockState().is(Blocks.STONE) || self.defaultBlockState().is(Blocks.DIORITE) || self.defaultBlockState().is(Blocks.GRANITE) || self.defaultBlockState().is(Blocks.ANDESITE) ) {
            cir.setReturnValue(1F);
        }
    }
}