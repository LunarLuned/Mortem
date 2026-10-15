package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.effect.ModEffects;
import net.lunarluned.mortem.misc.FungalTicksAccess;
import net.lunarluned.mortem.util.AnchorTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RespawnAnchorBlock.class)
public abstract class RespawnAnchorBlockMixin extends Block {
    public RespawnAnchorBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState old, boolean moved) {
        super.onPlace(state, level, pos, old, moved);
        if (!level.isClientSide() && !old.is(this)) level.scheduleTick(pos, this, 20);
    }

    @Override
    protected void tick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (state.getValue(RespawnAnchorBlock.CHARGE) > 0 && level.dimensionType().hasCeiling()) {
            AnchorTracker.add(level, pos);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(pos).inflate(AnchorTracker.RADIUS),
                    e -> e.hasEffect(ModEffects.FUNGALLY_INFECTED)
                            || ((FungalTicksAccess) e).mortem_getFungalTicks() > 0)) {
                e.removeEffect(ModEffects.FUNGALLY_INFECTED);
                ((FungalTicksAccess) e).mortem_setFungalTicks(0);
            }
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(pos).inflate(AnchorTracker.RADIUS),
                    e -> e.hasEffect(ModEffects.FUNGALLY_INFECTED))) {
                e.removeEffect(ModEffects.FUNGALLY_INFECTED);
            }
            Vec3 center = Vec3.atCenterOf(pos);
            for (ServerPlayer player : level.getPlayers(p -> p.distanceToSqr(center) <= AnchorTracker.RADIUS * AnchorTracker.RADIUS)) {
                level.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1, player.getZ(), 20, 4, 3, 4, 0.5);
            }
        }
        level.scheduleTick(pos, this, 20);
    }
}