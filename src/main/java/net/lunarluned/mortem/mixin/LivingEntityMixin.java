package net.lunarluned.mortem.mixin;

import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> holder);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> holder);

    @Shadow public abstract boolean removeAllEffects();

    @Shadow public abstract boolean addEffect(MobEffectInstance mobEffectInstance);

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super();
    }

    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
    private float multiplyDamageForVoidTouched(float amount) {
        if (this.hasEffect(MobEffects.WEAKNESS)) {
            return amount + (amount * (0.45f * (this.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1)));
        }
        return amount;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if ((this.hasEffect(MobEffects.WEAKNESS) && (this.hasEffect(MobEffects.REGENERATION)))) {
            this.removeAllEffects();
            this.addEffect(new MobEffectInstance(ModEffects.IMMUNE, 6000, 0));
        }
    }


    }
