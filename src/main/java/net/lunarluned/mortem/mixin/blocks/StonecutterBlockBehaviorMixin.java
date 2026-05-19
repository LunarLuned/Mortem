package net.lunarluned.mortem.mixin.blocks;

import net.lunarluned.mortem.RecycleResult;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class StonecutterBlockBehaviorMixin {

    @Inject(
            method = "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;Z)V",
            at = @At("TAIL")
    )
    public void mortem_entityInside(BlockState state, Level level, BlockPos pos, Entity entity,
                                    InsideBlockEffectApplier effectApplier, boolean isPrecise, CallbackInfo ci) {

        if (!state.is(Blocks.STONECUTTER)) return;

        Direction facing = state.getValue(StonecutterBlock.FACING);

        Vec3 blockCenter = Vec3.atCenterOf(pos);
        Vec3 entityPos = entity.position();

        Vec3 toEntity = entityPos.subtract(blockCenter);

        Vec3 facingVec = new Vec3(
                facing.getStepX(),
                facing.getStepY(),
                facing.getStepZ()
        );

        double dot = toEntity.normalize().dot(facingVec);

        if (dot > 0.3) {
            if (entity.tickCount % 10 == 0 && !entity.isInvulnerable() && !(entity instanceof ItemEntity)) {
                level.playSound(entity, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1, 1);
                entity.hurt(level.damageSources().generic(), 2.0F);
                entity.invulnerableTime = 0;
            }
            entity.makeStuckInBlock(state, new Vec3(0.75, 0.8, 0.75));
        }
    }
}
