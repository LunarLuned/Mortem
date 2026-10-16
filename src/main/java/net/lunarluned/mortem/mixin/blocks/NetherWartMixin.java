package net.lunarluned.mortem.mixin.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherWartBlock.class)
public class NetherWartMixin {

    @Inject(
            method = "randomTick",
            at = @At("HEAD"),
            cancellable = true)
    private void mortem_wartOnlyInNether(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {

        if (level.dimension() != Level.NETHER) {
            level.destroyBlock(pos, true);
            ci.cancel();
        }
    }
}