package net.lunarluned.mortem.effect;


import net.lunarluned.mortem.Mortem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ModEffects {

    public static Holder<MobEffect> IMMUNE;
    public static Holder<MobEffect> ENHANCED_IMMUNITY;
    public static Holder<MobEffect> ENHANCED_LUCK;
    public static Holder<MobEffect> ENHANCED_RESISTANCE;

    public static Holder<MobEffect> INFECTED;
    public static Holder<MobEffect> FUNGALLY_INFECTED;
    public static Holder<MobEffect> STAGNATED;

    private static final UUID FUNGAL_SPEED_UUID = UUID.fromString("9b5f8b8e-3f7a-4a3d-9c3a-1d1a2a4e7f10");

    private static Holder<MobEffect> register(String name, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.tryBuild(Mortem.MOD_ID, name), mobEffect);
    }

    public static void registerEffects() {
        IMMUNE = register("immune", new ImmuneEffect(MobEffectCategory.BENEFICIAL, 14264576));

        ENHANCED_IMMUNITY = register("enhanced_immune", new EnhancedImmunityEffect(MobEffectCategory.BENEFICIAL, 0xefd505));
        ENHANCED_LUCK = register("enhanced_luck", new EnhancedLuckEffect(MobEffectCategory.BENEFICIAL, 0xefd505));
        ENHANCED_RESISTANCE = register("enhanced_resistance", new EnhancedResistanceEffect(MobEffectCategory.BENEFICIAL, 0xefd505));


        INFECTED = register("infected", new InfectedEffect(MobEffectCategory.HARMFUL, 1657351));
        FUNGALLY_INFECTED = register("fungally_infected", new FungalInfectedEffect(MobEffectCategory.HARMFUL, 16253176).addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.parse("9b5f8b8e-3f7a-4a3d-9c3a-1d1a2a4e7f10"),
                -0.15D,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ));
        STAGNATED = register("stagnated", new StagnatedEffect(MobEffectCategory.HARMFUL, 1657351));
    }
}
