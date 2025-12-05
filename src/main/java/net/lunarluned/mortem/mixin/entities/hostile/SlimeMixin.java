package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {

    protected SlimeMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void mortem_slimeSpawnNaturally(EntityType<Slime> entityType, LevelAccessor levelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
            if (levelAccessor.getBrightness(LightLayer.BLOCK, blockPos) <= 8
                    && levelAccessor.getBrightness(LightLayer.SKY, blockPos) <= 8) {
                cir.setReturnValue(checkMobSpawnRules(entityType, levelAccessor, entitySpawnReason, blockPos, randomSource));
            }
    }
}
