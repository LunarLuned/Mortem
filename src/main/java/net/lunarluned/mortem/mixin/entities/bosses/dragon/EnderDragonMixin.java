package net.lunarluned.mortem.mixin.entities.bosses.dragon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends LivingEntity {

    protected EnderDragonMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
            if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                return true;
            }
        return super.isInvulnerableTo(serverLevel, damageSource);
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;createAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", cancellable = true)
    private static void mortem_changeDragonAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // buff dragon's health to 300, adds 20 armor
            cir.setReturnValue(Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.ARMOR, 15.0D));
    }
}
