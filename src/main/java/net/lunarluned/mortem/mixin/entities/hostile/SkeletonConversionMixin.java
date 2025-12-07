package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SkeletonConversionMixin {

    @Inject(method = "die", at = @At("RETURN"))
    private void spawnWitherSkeleton(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (self instanceof Skeleton && damageSource.is(DamageTypes.WITHER) || damageSource.is(DamageTypes.WITHER_SKULL)) {

            ServerLevel serverLevel = (ServerLevel) self.level();
            WitherSkeleton wSkeleton =
                    EntityType.WITHER_SKELETON.create(serverLevel, EntitySpawnReason.TRIGGERED);

            if (wSkeleton != null) {
                RandomSource randomSource = self.getRandom();

                float h = Mth.randomBetween(
                        randomSource,
                        -(float)Math.PI / 2F,
                        (float)Math.PI / 2F
                );

                Vector3f vector3f = self.getLookAngle().toVector3f()
                        .mul(0.3F)
                        .mul(1.0F, 1.5F, 1.0F)
                        .rotateY(h);

                wSkeleton.snapTo(
                        self.getX(),
                        self.getY(),
                        self.getZ(),
                        serverLevel.getRandom().nextFloat() * 360.0F,
                        0.0F
                );

                wSkeleton.setDeltaMovement(new Vec3(vector3f));
                serverLevel.addFreshEntity(wSkeleton);
                wSkeleton.playSound(SoundEvents.WITHER_SKELETON_HURT);
            }
        }
    }
}