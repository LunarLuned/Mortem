package net.lunarluned.mortem.mixin.entities.living_entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayer.class)
public abstract class HungerEffectsMixin extends Player {

    @Unique
    private static final ResourceLocation HUNGER_SLOWNESS_ID =
            ResourceLocation.tryBuild("mortem", "hunger_slowness");

    public HungerEffectsMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void mortem_applyHungerEffects(CallbackInfo ci) {
        if (this.level().isClientSide()) return;

        int hunger = this.getFoodData().getFoodLevel();
        double slowAmount = 0;

        if (hunger == 8) {
            slowAmount = -0.15;
        } else if (hunger == 4) {
            slowAmount = -0.35;
        }

        var modifiers = HashMultimap.<Holder<Attribute>, AttributeModifier>create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(HUNGER_SLOWNESS_ID, slowAmount,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        if (hunger == 4 || hunger == 8) {
            this.getAttributes().addTransientAttributeModifiers(modifiers);
        }
        else if (hunger > 8) {
            this.getAttributes().removeAttributeModifiers(modifiers);
        }

        if (hunger <= 3) {
            this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0, true, false));
        }
        if (hunger <= 2) {
            this.addEffect(new MobEffectInstance(ModEffects.STAGNATED, 20, 1, true, false));
        }
    }
}