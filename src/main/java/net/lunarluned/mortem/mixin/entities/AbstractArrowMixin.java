package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {

    @Unique
    private int blocksBroken = 0;


    @Shadow protected abstract void setInGround(boolean bl);

    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "onHitBlock")
    public void onHitBlock(BlockHitResult bHR, CallbackInfo ci) {

        // code was heavily referenced off of MarsThePlanet's MIT code of FireArrowsIgniteFire, thank you greatly!
        if(bHR.getType() == HitResult.Type.MISS) {return;}
        if(!this.isOnFire()) {return;}
        Level level = this.level();
        if(level instanceof ServerLevel) {
            switch(bHR.getDirection()) {
                case Direction.UP:
                    startFire(bHR.getBlockPos().above(), level);
                    break;
                case Direction.DOWN:
                    startFire(bHR.getBlockPos().below(), level);
                    break;
                case Direction.EAST:
                    startFire(bHR.getBlockPos().east(), level);
                    break;
                case Direction.WEST:
                    startFire(bHR.getBlockPos().west(), level);
                    break;
                case Direction.NORTH:
                    startFire(bHR.getBlockPos().north(), level);
                    break;
                case Direction.SOUTH:
                    startFire(bHR.getBlockPos().south(), level);
                    break;
            }
        }
    }

    @Unique
    public void startFire(BlockPos firePosition, Level level) {
                level.destroyBlock(firePosition, true);
                level.setBlock(firePosition, BaseFireBlock.getState(level, firePosition), 11);
        }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        Vec3 vec33 = this.position();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.isOnFire()) {
            if (level().getBiomeManager().getBiome(blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, vec33.x + vec3.x, vec33.y + vec3.y, vec33.z + vec3.z, -vec3.x, -vec3.y + 0.2, -vec3.z);
            } else {
                this.level().addParticle(ParticleTypes.FLAME, vec33.x + vec3.x, vec33.y + vec3.y, vec33.z + vec3.z, -vec3.x, -vec3.y + 0.2, -vec3.z);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onHitBlock", cancellable = true)
    private void breakGlass(BlockHitResult blockHitResult, CallbackInfo info) {
        if (this.level().getBlockState(blockHitResult.getBlockPos()).is(MortemTags.ARROW_BREAKABLE) && blocksBroken < 2) {
            blocksBroken++;
            this.level().destroyBlock(blockHitResult.getBlockPos(), true);
            Vec3 motion = this.getDeltaMovement();
            if (motion.lengthSqr() < 0.01) {
                Vec3 newMotion = motion.normalize().scale(0.9);
                this.setDeltaMovement(newMotion);
            }
            // Un-stick the arrow
            this.setInGround(false);

            // Prevent vanilla from stopping it
            info.cancel();
        }
    }
}
