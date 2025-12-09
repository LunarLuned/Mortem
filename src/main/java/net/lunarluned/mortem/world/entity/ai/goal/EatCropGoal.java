package net.lunarluned.mortem.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class EatCropGoal extends Goal {
    private final Zombie zombie;
    private final Level level;
    private BlockPos targetPos;
    private final double speed;
    private int eatCooldown;

    private final int SEARCH_RADIUS = 8;

    public EatCropGoal(Zombie zombie, double speed) {
        this.zombie = zombie;
        this.level = zombie.level();
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (zombie.isVehicle() || zombie.isPassenger()) return false;
        if (eatCooldown > 0) {
            eatCooldown--;
            return false;
        }

        BlockPos zpos = zombie.blockPosition();
        for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
                    BlockPos pos = zpos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();
                    if (isCropBlock(block)) {
                        // prefer mature crops first
                        if (block instanceof CropBlock crop) {
                            if (crop.isMaxAge(state)) {
                                targetPos = pos;
                                return true;
                            } else {
                                targetPos = pos;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        eatCooldown = 200 + zombie.getRandom().nextInt(200);
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (targetPos == null) return false;
        if (zombie.getHealth() >= zombie.getMaxHealth()) return false;
        if (!level.getBlockState(targetPos).isAir()) {
            double dx = targetPos.getX() + 0.5 - zombie.getX();
            double dy = targetPos.getY() - zombie.getY();
            double dz = targetPos.getZ() + 0.5 - zombie.getZ();
            double distSq = dx*dx + dy*dy + dz*dz;
            return distSq <= (SEARCH_RADIUS * SEARCH_RADIUS);
        }
        return false;
    }

    @Override
    public void start() {
        if (targetPos != null) {
            PathNavigation nav = zombie.getNavigation();
            nav.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, speed);
        }
    }

    @Override
    public void stop() {
        targetPos = null;
        eatCooldown = 20;
    }

    @Override
    public void tick() {
        if (targetPos == null) return;

        double dx = targetPos.getX() + 0.5 - zombie.getX();
        double dy = targetPos.getY() - zombie.getY();
        double dz = targetPos.getZ() + 0.5 - zombie.getZ();
        double distSq = dx*dx + dy*dy + dz*dz;

        if (zombie.getNavigation().isDone()) {
            zombie.getNavigation().moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, speed);
        }

        int MAX_REACH_SQ = 4;
        if (distSq <= MAX_REACH_SQ) {
            // attempt to "eat" the crop
            BlockState state = level.getBlockState(targetPos);
            if (state.getBlock() instanceof CropBlock crop) {
                if (crop.isMaxAge(state)) {
                    if (!level.isClientSide()) {
                        level.destroyBlock(targetPos, false, zombie);
                    }
                    level.playSound(zombie, this.zombie.xo, this.zombie.yo, this.zombie.zo, SoundEvents.GENERIC_EAT, this.zombie.getSoundSource(), 1.0F, 1.0F);
                    zombie.heal(4.0F);
                } else {
                    if (!level.isClientSide()) {
                        level.destroyBlock(targetPos, false, zombie);
                    }
                        zombie.heal(1.0F);
                        level.playSound(zombie, this.zombie.xo, this.zombie.yo, this.zombie.zo, SoundEvents.GENERIC_EAT, this.zombie.getSoundSource(), 1.0F, 1.0F);
                    }
            }
            eatCooldown = 100 + zombie.getRandom().nextInt(100);
            targetPos = null;
            zombie.getNavigation().stop();
        }
    }

    private boolean isCropBlock(Block block) {
        return block instanceof CropBlock
                || block == Blocks.WHEAT
                || block == Blocks.CARROTS
                || block == Blocks.POTATOES
                || block == Blocks.BEETROOTS;
    }
}