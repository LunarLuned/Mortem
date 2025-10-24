package net.lunarluned.mortem.mixin.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        Vec3 vec33 = this.position();
        Vec3 vec3 = this.getDeltaMovement();
        BlockPos blockPos = this.blockPosition();
        if (this.isOnFire()) {
                this.level().addParticle(ParticleTypes.FLAME, vec33.x + vec3.x, vec33.y + vec3.y, vec33.z + vec3.z, -vec3.x, -vec3.y + 0.2, -vec3.z);
    }
    }
}
