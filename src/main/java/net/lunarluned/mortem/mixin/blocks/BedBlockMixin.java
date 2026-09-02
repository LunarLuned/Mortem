package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin {

    @Inject(
            method = "useWithoutItem",
            at = @At("HEAD"),
            cancellable = true)

    private void mortem_replaceExplosionWithCarpet(
            BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        BlockPos center = BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
            if (!level.isClientSide()) {
                BedRule bedRule = level.environmentAttributes()
                        .getValue(EnvironmentAttributes.BED_RULE, pos);

                BedPart part = state.getValue(BedBlock.PART);
                Direction facing = state.getValue(BedBlock.FACING);
                BlockPos otherPos = (part == BedPart.HEAD) ? center.relative(facing.getOpposite()) : center.relative(facing);

                if (bedRule.explodes()) {
                    player.sendOverlayMessage(
                            Component.translatable("mortem.gameplay.cannot_rest")
                    );

                    // TODO: Add logic for bed breaking


                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                    cir.cancel();
                }
            }
    }
}