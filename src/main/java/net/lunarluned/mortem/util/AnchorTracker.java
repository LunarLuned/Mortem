package net.lunarluned.mortem.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public final class AnchorTracker {
    public static final int RADIUS = 64;
    private static final Map<ResourceKey<Level>, Set<BlockPos>> ANCHORS = new HashMap<>();

    public static void add(Level level, BlockPos pos) {
        ANCHORS.computeIfAbsent(level.dimension(), k -> new HashSet<>()).add(pos.immutable());
    }

    public static boolean isProtected(Level level, Vec3 at) {
        Set<BlockPos> set = ANCHORS.get(level.dimension());
        if (set == null) return false;
        Iterator<BlockPos> it = set.iterator();
        while (it.hasNext()) {
            BlockPos p = it.next();
            if (!level.isLoaded(p)) continue;
            BlockState s = level.getBlockState(p);
            if (!s.is(Blocks.RESPAWN_ANCHOR) || s.getValue(RespawnAnchorBlock.CHARGE) == 0) { it.remove(); continue; }
            if (Vec3.atCenterOf(p).distanceToSqr(at) <= RADIUS * RADIUS) return true;
        }
        return false;
    }
}