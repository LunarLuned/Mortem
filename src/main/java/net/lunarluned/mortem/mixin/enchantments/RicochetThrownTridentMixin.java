package net.lunarluned.mortem.mixin.enchantments;

import net.lunarluned.mortem.enchantments.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;

@Mixin(ThrownTrident.class)
public abstract class RicochetThrownTridentMixin {

    @Shadow public abstract ItemStack getWeaponItem();

    @Shadow private boolean dealtDamage;
    @Unique
    private static final WeakHashMap<ThrownTrident, Integer> RICOCHET_COUNTS = new WeakHashMap<>();

    @Inject(method = "onHitEntity", at = @At("TAIL"))
    private void onHitEntity(EntityHitResult result, CallbackInfo ci) {
        ThrownTrident trident = (ThrownTrident)(Object)this;
        Level level = trident.level();

        if (level.isClientSide()) return;

        ItemStack tridentStack = this.getWeaponItem();
        if (tridentStack == null || tridentStack.isEmpty()) return;

        Holder<Enchantment> ricochetHolder;
        try {
            ricochetHolder = level.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(ModEnchantments.RICOCHET);
        } catch (Exception ex) {
            return;
        }

        int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(ricochetHolder, tridentStack);
        if (enchLevel <= 0) return;

        int used = RICOCHET_COUNTS.getOrDefault(trident, 0);
        int max = 4;
        if (used >= max) return;
        RICOCHET_COUNTS.put(trident, used + 1);

        Entity hitEntity = result.getEntity();
        if (hitEntity == null) return;

        Entity owner = trident.getOwner();

        double radius = 4.0 + enchLevel * 2.0;
        List<LivingEntity> nearby = level.getEntitiesOfClass(
                LivingEntity.class,
                trident.getBoundingBox().inflate(radius),
                e -> e != hitEntity && e != owner && e.isAlive());

        if (nearby.isEmpty()) return;

        LivingEntity target = nearby.stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(hitEntity)))
                .orElse(null);

        if (target == null) return;

        if (used < max) {
            this.dealtDamage = false;
        }

            // fallback: redirect current trident
        double speed = 1.8 + enchLevel * 0.4;
            trident.setDeltaMovement(
                    target.position()
                            .add(0, target.getEyeHeight() * 0.5, 0)
                            .subtract(trident.position())
                            .normalize()
                            .scale(speed)
            );
        }
    }