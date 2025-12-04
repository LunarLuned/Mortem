package net.lunarluned.mortem.mixin.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void mortem_spreadBonemeal(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {

        // i had studied someone elses code for this and basically tried to optimize it as much as possible.
        // all it does really is just apply bone meal to nearby crops like how it does for grass

        Level level = useOnContext.getLevel();

        if (!(level instanceof ServerLevel) || level.isClientSide()) {
            return;
        }

        BlockPos pos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        // if its not a cropblock and it isnt max age DONT DO IT !!!!!!!
        if (!(block instanceof CropBlock cropBlock) || cropBlock.isMaxAge(blockState)) {
            return;
        }

        int radius = 2;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos nearbyPos = pos.offset(dx, 0, dz);
                BlockState nearbyState = level.getBlockState(nearbyPos);
                Block nearbyBlock = nearbyState.getBlock();

                if (!(nearbyBlock instanceof CropBlock nearbyCropBlock) || nearbyCropBlock.isMaxAge(nearbyState)) {
                    continue;
                }

                double distance = Math.sqrt(dx * dx + dz * dz);
                double maxDistance = Math.sqrt(2 * (radius * radius));

                double probability = 1.0 - (distance / maxDistance);

                if (level.random.nextDouble() > probability) {
                    continue;
                }

                BoneMealItem.growCrop(useOnContext.getItemInHand(), level, nearbyPos);
                ((ServerLevel) level).getChunkSource().blockChanged(nearbyPos);
                spawnBoneMealParticles((ServerLevel) level, nearbyPos);
            }
        }

        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    @Unique
    private void spawnBoneMealParticles(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 10; i++) {
            double offsetX = level.random.nextDouble();
            double offsetY = level.random.nextDouble() * 0.5 + 0.5;
            double offsetZ = level.random.nextDouble();
            double deltaY = level.random.nextDouble() * 0.1;
            double speed = level.random.nextDouble() * 0.05;

            Vec3 particlePos = new Vec3(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, particlePos.x, particlePos.y, particlePos.z, 1, 0, deltaY, 0, speed);
        }
    }
}