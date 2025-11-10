package net.lunarluned.mortem.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.TeleportRandomlyConsumeEffect;

import java.util.List;

public class ModConsumables {
    public static final Consumable DRUG;
    public static final Consumable RESIN_CANDY;

    public static final Consumable CRIMSON_STEW;
    public static final Consumable WARPED_STEW;
    public static final Consumable FUNGAL_STEW;
    public static final Consumable MUSHROOM_STEW_TUSK;
    public static final Consumable SCRAMBLED_EGGS_TUSK;

    public static final Consumable SUSHI_ROLL;

    public static final Consumable SWEET_POTATO;
    public static final Consumable RAW_BACON;
    public static final Consumable COOKED_BACON;
    public static final Consumable BEEF_PATTY;
    public static final Consumable COOKED_BEEF_PATTY;
    public static final Consumable BURGER;
    public static final Consumable BACON_BURGER;
    public static final Consumable TOAST;
    public static final Consumable TOASTED_BREAD;

    public static final Consumable SWEET_BERRY_JAM;
    public static final Consumable APPLE_JAM;
    public static final Consumable CACTUS_JAM;
    public static final Consumable COCOA_SPREAD;
    public static final Consumable MUSHROOM_PUREE;
    public static final Consumable PORK_PATE;

    public static final Consumable BERRY_SPREAD_TOAST;
    public static final Consumable APPLE_SPREAD_TOAST;
    public static final Consumable CACTUS_SPREAD_TOAST;
    public static final Consumable COCOA_SPREAD_TOAST;
    public static final Consumable MUSHROOM_SPREAD_TOAST;
    public static final Consumable PORK_SPREAD_TOAST;

    public ModConsumables() {
    }

    public static Consumable.Builder defaultFood() {
        return Consumable.builder().consumeSeconds(1.6F).animation(ItemUseAnimation.EAT).sound(SoundEvents.GENERIC_EAT).hasConsumeParticles(true);
    }

    public static Consumable.Builder defaultDrink() {
        return Consumable.builder().consumeSeconds(1.6F).animation(ItemUseAnimation.DRINK).sound(SoundEvents.GENERIC_DRINK).hasConsumeParticles(false);
    }

    static {
        DRUG = defaultFood().consumeSeconds(0.3F).sound(SoundEvents.GENERIC_EAT).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 250, 1), 0.5F)).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 300, 1), 0.8F)).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.9F)).build();
        RESIN_CANDY = defaultFood().consumeSeconds(0.5F).sound(SoundEvents.GENERIC_EAT).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 250, 0), 0.1F)).build();

        CRIMSON_STEW = defaultDrink().consumeSeconds(1.55F).build();
        WARPED_STEW = defaultDrink().consumeSeconds(1.55F).build();
        FUNGAL_STEW = defaultDrink().consumeSeconds(2.25F).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0), 0.75F)).build();
        MUSHROOM_STEW_TUSK = defaultDrink().consumeSeconds(1F).build();
        SCRAMBLED_EGGS_TUSK = defaultDrink().consumeSeconds(2F).build();

        SUSHI_ROLL = defaultFood().consumeSeconds(1.15F).build();

        SWEET_POTATO = defaultFood().consumeSeconds(1F).sound(SoundEvents.GENERIC_EAT).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION, 500, 0), 0.9F)).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 500, 0), 0.9F)).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 0), 0.25F)).build();
        RAW_BACON = defaultFood().consumeSeconds(.75F).sound(SoundEvents.GENERIC_EAT).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 100, 1), 0.7F)).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 1), 0.7f)).build();
        COOKED_BACON = defaultFood().consumeSeconds(0.45F).build();
        BEEF_PATTY = defaultFood().consumeSeconds(.95F).sound(SoundEvents.GENERIC_EAT).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 100, 1), 0.7F)).build();
        COOKED_BEEF_PATTY = defaultFood().consumeSeconds(0.75F).build();
        BURGER = defaultFood().consumeSeconds(2.35F).build();
        BACON_BURGER = defaultFood().consumeSeconds(2.45F).build();
        TOAST = defaultFood().consumeSeconds(0.55f).build();
        TOASTED_BREAD = defaultFood().consumeSeconds(1.0F).build();

        SWEET_BERRY_JAM = defaultDrink().consumeSeconds(1.25f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 0), 1F)).build();
        APPLE_JAM = defaultDrink().consumeSeconds(1.65f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0), 1F)).build();
        CACTUS_JAM = defaultDrink().consumeSeconds(1.4f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 120, 0), 1F)).build();
        COCOA_SPREAD = defaultDrink().consumeSeconds(1.25f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 40, 0), 1F)).build();
        MUSHROOM_PUREE = defaultDrink().consumeSeconds(1.45f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0), 1F)).build();
        PORK_PATE = defaultDrink().consumeSeconds(1.85f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0), 1F)).build();

        BERRY_SPREAD_TOAST = defaultFood().consumeSeconds(.65f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 100, 0), .65F)).build();
        APPLE_SPREAD_TOAST = defaultFood().consumeSeconds(.8f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0), .5F)).build();
        CACTUS_SPREAD_TOAST = defaultFood().consumeSeconds(1f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 60, 0), .25F)).build();
        COCOA_SPREAD_TOAST = defaultFood().consumeSeconds(.5f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 140, 0), .6F)).build();
        MUSHROOM_SPREAD_TOAST = defaultFood().consumeSeconds(.9f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0), .5F)).build();
        PORK_SPREAD_TOAST = defaultFood().consumeSeconds(1.2f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SLOWNESS, 30, 0), 0.2F)).build();



        /*OMINOUS_BOTTLE = defaultDrink().soundAfterConsume(SoundEvents.OMINOUS_BOTTLE_DISPOSE).build();
        DRIED_KELP = defaultFood().consumeSeconds(0.8F).build();
        CHICKEN = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)).build();
        ENCHANTED_GOLDEN_APPLE = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.REGENERATION, 400, 1), new MobEffectInstance(MobEffects.RESISTANCE, 6000, 0), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3)))).build();
        GOLDEN_APPLE = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0)))).build();
        POISONOUS_POTATO = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), 0.6F)).build();
        PUFFERFISH = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.POISON, 1200, 1), new MobEffectInstance(MobEffects.HUNGER, 300, 2), new MobEffectInstance(MobEffects.NAUSEA, 300, 0)))).build();
        ROTTEN_FLESH = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F)).build();
        SPIDER_EYE = defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 100, 0))).build();
        MILK_BUCKET = defaultDrink().onConsume(ClearAllStatusEffectsConsumeEffect.INSTANCE).build();
        CHORUS_FRUIT = defaultFood().onConsume(new TeleportRandomlyConsumeEffect()).build();

         */
    }
}
