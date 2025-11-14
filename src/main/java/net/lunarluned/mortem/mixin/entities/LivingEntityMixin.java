package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.MortemTags;
import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> holder);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> holder);

    @Shadow public abstract boolean removeAllEffects();

    @Shadow public abstract boolean addEffect(MobEffectInstance mobEffectInstance);

    @Shadow public abstract boolean removeEffect(Holder<MobEffect> holder);

    @Shadow @Nullable public abstract LivingEntity asLivingEntity();

    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
    private float mortem_multiplyDamageForWeakness(float amount) {
        if (this.hasEffect(MobEffects.WEAKNESS)) {
            return amount + (amount * (0.45f * (Objects.requireNonNull(this.getEffect(MobEffects.WEAKNESS)).getAmplifier() + 1)));
        }
        return amount;
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void mortem_enderManStunShield(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            // Endermen stun shields now for 5 seconds.
            if (damageSource.getEntity() instanceof EnderMan) {
                if (player.isBlocking()) {
                    player.getCooldowns().addCooldown(new ItemStack(Items.SHIELD), 100);
                    player.stopUsingItem();
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.HOSTILE, 1.0F, 1);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.HOSTILE, 1.0F, 0.5f);
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Unique
    private int fireTickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void mortem_applyWeaknessWhenOnFire(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.isOnFire()) {
            if (self instanceof Monster && fireTickCounter > 1) {
                self.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1, false, true));
            }
            fireTickCounter++;
            if (fireTickCounter > 60) {
                    self.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0, false, true));
                }
            if (fireTickCounter > 200) {
                    self.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1, false, true));
                }
            if (fireTickCounter > 400) {
                    self.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2, false, true));
                }
        } else {
            if (fireTickCounter > 60) {
                self.removeEffect(MobEffects.WEAKNESS);
            }
            fireTickCounter = 0;
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void mortem_infectDamageTake(CallbackInfo ci) {
        Level var19 = this.level();
        if (var19 instanceof ServerLevel serverLevel) {
        if (this.hasEffect(ModEffects.INFECTED) && Objects.requireNonNull(this.getEffect(ModEffects.INFECTED)).endsWithin(600) && !this.isInvulnerable()) {

            this.hurtServer(serverLevel, this.damageSources().starve(), 1F);

        }
        }

    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void mortem_cancelOutEffects(CallbackInfo ci) {
        if ((this.hasEffect(MobEffects.WEAKNESS) && (this.hasEffect(MobEffects.REGENERATION)) && (this.hasEffect(ModEffects.INFECTED)))) {
            if ((this.tickCount % 10 == 0)) {
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(ModEffects.IMMUNE, 400, 0));
            }
        }

        if ((this.hasEffect(ModEffects.INFECTED)) && this.getType().is(MortemTags.CANNOT_BE_ZOMBIFIED)) {
                this.removeEffect(ModEffects.INFECTED);
        }

        if ((this.hasEffect(ModEffects.IMMUNE)) && (this.hasEffect(ModEffects.INFECTED))) {
            if ((this.tickCount % 10 == 0)) {
                this.removeEffect(ModEffects.INFECTED);
            }
        }

        if ((this.hasEffect(ModEffects.IMMUNE)) && (this.hasEffect(ModEffects.FUNGALLY_INFECTED))) {
            if ((this.tickCount % 10 == 0)) {
                this.removeEffect(ModEffects.FUNGALLY_INFECTED);
            }
        }

        if ((this.hasEffect(MobEffects.REGENERATION)) && (Objects.requireNonNull(this.getEffect(MobEffects.REGENERATION)).getAmplifier() >= 1 && (this.hasEffect(ModEffects.STAGNATED)))) {
            int regenDuration = Objects.requireNonNull(this.getEffect(MobEffects.REGENERATION)).getDuration();
            this.removeEffect(ModEffects.STAGNATED);
            this.removeEffect(MobEffects.REGENERATION);
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, regenDuration /2, 1));
        }
    }



    @Unique
    private int fireDamageCooldown = 0;

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void mortem_preventFireIFrames(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        // Only modify fire damage
        if (damageSource == damageSources().onFire() || damageSource == damageSources().lava()) {

            // Handle separate cooldown
            if (fireDamageCooldown > 0) {
                cir.setReturnValue(false); // cancel damage this tick
                return;
            }

            fireDamageCooldown = 20; // e.g., 1 second cooldown
            self.invulnerableTime = 0; // ignore normal i-frames
        }
    }

    // Tick down the cooldown every tick
    @Inject(method = "tick", at = @At("HEAD"))
    private void mortem_tickFireCooldown(CallbackInfo ci) {
        if (fireDamageCooldown > 0) fireDamageCooldown--;
    }

    }
