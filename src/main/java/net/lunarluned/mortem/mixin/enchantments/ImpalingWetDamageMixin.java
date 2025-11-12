package net.lunarluned.mortem.mixin.enchantments;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.lunarluned.mortem.misc.EnchantmentHolderHelper.resolveHolder;

@Mixin(ThrownTrident.class)
public abstract class ImpalingWetDamageMixin extends AbstractArrow {

    @Shadow private boolean dealtDamage;
    @Unique
    Level level = this.level();

    @Unique
    ResourceKey<Enchantment> enchantKey = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild("minecraft", "impaling"));
    @Unique
    ResourceKey<Enchantment> loyaltyenchantKey = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild("minecraft", "loyalty"));

    @Unique
    Holder.Reference<Enchantment> holder = resolveHolder(level, Registries.ENCHANTMENT, enchantKey);
    @Unique
    Holder.Reference<Enchantment> loyaltyholder = resolveHolder(level, Registries.ENCHANTMENT, loyaltyenchantKey);

    protected ImpalingWetDamageMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void onHit(EntityHitResult result, CallbackInfo ci) {
        ThrownTrident trident = (ThrownTrident)(Object)this;
        Entity owner = trident.getOwner();
        if (!(owner instanceof LivingEntity)) return;

        ItemStack stack = trident.getWeaponItem();
        int impaling = EnchantmentHelper.getItemEnchantmentLevel(holder, stack);

        if (impaling > 0) {
            boolean wet = result.getEntity().isInWaterOrRain();
            if (wet && owner.isInWaterOrRain()) {
                result.getEntity().hurt(trident.damageSources().trident(trident, owner), (impaling * 3F) + 8);
            } else
            if (wet || owner.isInWaterOrRain()) {
                // Extra damage + armor penetration
                result.getEntity().hurt(trident.damageSources().trident(trident, owner), (impaling * 2F) + 8);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void returnFromVoid(CallbackInfo ci) {
        ThrownTrident t = (ThrownTrident)(Object)this;
        int loyalty = EnchantmentHelper.getItemEnchantmentLevel(loyaltyholder, t.getWeaponItem());
        if (loyalty > 0 && t.getY() < t.level().getMinY()) {
            this.dealtDamage = true;
            t.setDeltaMovement(0, 1.5, 0); // rise up out of void
        }
    }
}