package net.lunarluned.mortem.mixin.entities.living_entity;

import net.lunarluned.mortem.MortemTags;
import net.lunarluned.mortem.effect.ModEffects;
import net.lunarluned.mortem.sounds.MortemSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFungalInfectionMixin extends Entity {

    @Unique private int fungalBiomeTicks = 0;
    @Unique private static final int TICKS_REQUIRED = 18000;
    @Unique private static final int APPLIED_EFFECT_DURATION = 18000;

    public LivingEntityFungalInfectionMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mortem_fungalTick(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self == null) return;

        Level level = self.level();
        if (level == null) return;

        if (level.isClientSide()) return;

        BlockPos pos = self.blockPosition();
        Holder<Biome> biomeHolder = level.getBiome(pos);

        boolean inTarget = false;
        Optional<ResourceKey<Biome>> optKey = biomeHolder.unwrapKey();
        if (optKey.isPresent()) {
            ResourceKey<Biome> key = optKey.get();
            ResourceLocation id = key.location();
            String idStr = id.toString();
            if ("minecraft:warped_forest".equals(idStr) || "minecraft:crimson_forest".equals(idStr)) {
                inTarget = true;
            }
        } else {
        }

        if (!this.getType().is(MortemTags.FUNGUS_IMMUNE)) {
            if (inTarget) {
                fungalBiomeTicks++;
                if (!this.level().isClientSide()) {
                    if (fungalBiomeTicks > 1 && (this.tickCount % 1800 == 0)) {
                        self.level().playSound(self, self.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 1);
                    }
                }

                if (!self.hasEffect(ModEffects.ENHANCED_IMMUNITY)) {
                    if ((fungalBiomeTicks >= TICKS_REQUIRED) || self.getHealth() <= self.getMaxHealth() / 2.0F) {
                        if (!self.hasEffect(ModEffects.IMMUNE) && !self.hasEffect(ModEffects.FUNGALLY_INFECTED)) {
                            self.addEffect(new MobEffectInstance(ModEffects.FUNGALLY_INFECTED, APPLIED_EFFECT_DURATION, 0, false, true));
                            self.level().playSound(self, self.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1, 2);
                            self.level().playSound(self, self.blockPosition(), MortemSoundEvents.FUNGAL_TERRORS, SoundSource.PLAYERS, 1F, 0.1F);
                            fungalBiomeTicks = 0;
                        }
                    }
                } else {
                        fungalBiomeTicks = 0;
                    }

            } else {
                if (fungalBiomeTicks > 0) {
                    fungalBiomeTicks = Math.max(0, fungalBiomeTicks - 5);
                }
            }
        } else {
            return;
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mortem_writeNbt(ValueOutput valueOutput, CallbackInfo ci) {
        valueOutput.putInt("fungal", this.fungalBiomeTicks);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mortem_readNbt(ValueInput valueInput, CallbackInfo ci) {
        if (valueInput.contains("fungal")) {
            this.fungalBiomeTicks = valueInput.getIntOr("fungal", 0);
        }
    }
}

