package net.lunarluned.mortem.mixin.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public abstract class RaidMixin {

    @Shadow @Final
    private BlockPos center;

    @Inject(
            method = "findRandomSpawnPos",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mortem_forceSurfaceRaidSpawns(ServerLevel level, int maxTries, CallbackInfoReturnable<BlockPos> cir) {

        RandomSource random = level.getRandom();

        for (int i = 0; i < maxTries; i++) {

            float angle = random.nextFloat() * ((float)Math.PI * 2F);

            int distance = 32;

            int x = center.getX() + Mth.floor(Mth.cos(angle) * distance);
            int z = center.getZ() + Mth.floor(Mth.sin(angle) * distance);

            BlockPos topPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, 0, z));

            if (!level.canSeeSky(topPos)) {
                continue;
            }

            BlockPos belowPos = topPos.below();
            BlockState belowState = level.getBlockState(belowPos);

            if (!belowState.isSolidRender()) {
                continue;
            }

            cir.setReturnValue(topPos);
            return;
        }
    }
}