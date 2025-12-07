package net.lunarluned.mortem.mixin.entities.hostile;

import net.lunarluned.mortem.world.entity.ai.goal.HeavyLeapAtGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Spider.class)
public abstract class SpiderMixin extends Monster {

    protected SpiderMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void onRegisterGoals(CallbackInfo ci) {
        Spider self = (Spider) (Object) this;
        self.targetSelector.addGoal(3, new HeavyLeapAtGoal(self));
    }

    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
        if (super.doHurtTarget(serverLevel, entity)) {
            if (entity instanceof LivingEntity && entity.getRandom().nextInt(9) > 6) {
                BlockPos pos = entity.getOnPos().above();
                if (this.level().getBlockState(pos).isAir())
                    this.level().setBlockAndUpdate(pos, Blocks.COBWEB.defaultBlockState());
            }
            }
        return false;
    }

    @Override
    public boolean causeFallDamage(double d, float f, DamageSource damageSource) {
        return false;
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void modifyFollowRange(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();

        builder.add(Attributes.FOLLOW_RANGE, 48);

        cir.setReturnValue(builder);
    }
}
