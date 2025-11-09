package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFungalInfectionMixin extends Entity {


    @Shadow public abstract boolean hasEffect(Holder<MobEffect> holder);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> holder);

    @Shadow public abstract boolean addEffect(MobEffectInstance mobEffectInstance);

    @Unique private int fungalEffectTicks = 0;
    @Unique private int tempDuration = 0;

    @Unique private int fungalBiomeTicks = 0;
    @Unique
    private static final int TICKS_REQUIRED = 18000;
    @Unique private static final int APPLIED_EFFECT_DURATION = 18000;

    public LivingEntityFungalInfectionMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mortem_fungalTick(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (self == null) {
            return;
        }

        Level level = self.level();
        if (level == null || level.isClientSide()) return;

        BlockPos pos = self.blockPosition();

        // get the biome holder at the entity's current pos
        Holder<Biome> biomeHolder = level.getBiome(pos);

        // Try to unwrap the registry key to check biome id
        Optional<ResourceKey<Biome>> optKey = biomeHolder.unwrapKey();
        boolean inTarget = false;
        if (optKey.isPresent()) {
            ResourceKey<Biome> key = optKey.get();
            ResourceLocation id = key.location();
            String idStr = id.toString();
            if ("minecraft:warped_forest".equals(idStr) || "minecraft:crimson_forest".equals(idStr)) {
                inTarget = true;
            }
        } else {

        }

        if (inTarget) {
            fungalBiomeTicks++;
            if ((fungalBiomeTicks >= TICKS_REQUIRED) || self.getHealth() <= self.getMaxHealth() / 2) {
                if (!self.hasEffect(ModEffects.IMMUNE)) {
                    if (!self.hasEffect(ModEffects.FUNGAL_INFECTED)) {
                        self.addEffect(new MobEffectInstance(ModEffects.FUNGAL_INFECTED, APPLIED_EFFECT_DURATION, 0, false, true));
                    }
                }
                fungalBiomeTicks = 0;
            }
        } else {
            // reset the counter on leaving
            fungalBiomeTicks = 0;
            self.removeEffect(ModEffects.FUNGAL_INFECTED);
        }
    }


}