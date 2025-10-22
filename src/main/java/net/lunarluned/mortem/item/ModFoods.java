package net.lunarluned.mortem.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties DRUG = new FoodProperties.Builder().nutrition(1).saturationModifier(0.25f).alwaysEdible().build();
    public static final FoodProperties RESIN_CANDY = new FoodProperties.Builder().nutrition(1).saturationModifier(.1f).alwaysEdible().build();

    // Nether Foods

    public static final FoodProperties CRIMSON_STEW = (new FoodProperties.Builder().nutrition(4).saturationModifier(.7F).build());
    public static final FoodProperties WARPED_STEW = (new FoodProperties.Builder().nutrition(3).saturationModifier(.8F).build());
    public static final FoodProperties FUNGAL_STEW = (new FoodProperties.Builder().nutrition(6).saturationModifier(.55F).build());
    public static final FoodProperties MUSHROOM_STEW_TUSK = (new FoodProperties.Builder().nutrition(3).saturationModifier(.9F).build());
    public static final FoodProperties SCRAMBLED_EGGS_TUSK = (new FoodProperties.Builder().nutrition(5).saturationModifier(.65F).build());

    public static final FoodProperties HARD_BOILED_EGG = (new FoodProperties.Builder().nutrition(2).saturationModifier(.5f).build());
    public static final FoodProperties SCRAMBLED_EGGS = (new FoodProperties.Builder().nutrition(6).saturationModifier(.45F).build());

    public static final FoodProperties SUSHI_ROLL = (new FoodProperties.Builder().nutrition(4).saturationModifier(.25F).build());

    // Overworld Foods

    public static final FoodProperties SWEET_POTATO = (new FoodProperties.Builder().nutrition(3).saturationModifier(1.5f).build());
    public static final FoodProperties RAW_BACON = (new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build());
    public static final FoodProperties COOKED_BACON = (new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build());
    public static final FoodProperties BEEF_PATTY = (new FoodProperties.Builder().nutrition(3).saturationModifier(0.0f).build());
    public static final FoodProperties COOKED_BEEF_PATTY = (new FoodProperties.Builder().nutrition(4).saturationModifier(.25f).build());
    public static final FoodProperties BURGER = (new FoodProperties.Builder().nutrition(8).saturationModifier(.65f).build());
    public static final FoodProperties BACON_BURGER = (new FoodProperties.Builder().nutrition(10).saturationModifier(0.7f).build());
    public static final FoodProperties TOAST = (new FoodProperties.Builder().nutrition(2).saturationModifier(.25f).build());
    public static final FoodProperties TOASTED_BREAD = (new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).build());
/*
    public static final FoodProperties SWEET_BERRY_JAM = (new FoodProperties.Builder().nutrition(6).saturationMod(3f).fast().effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0), 1F).build());
    public static final FoodProperties APPLE_JAM = (new FoodProperties.Builder().nutrition(6).saturationMod(4f).fast().effect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0), 1F).build());
    public static final FoodProperties CACTUS_JAM = (new FoodProperties.Builder().nutrition(7).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0), 0.7F).build());
    public static final FoodProperties COCOA_SPREAD = (new FoodProperties.Builder().nutrition(4).saturationMod(1f).fast().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0), 1F).build());
    public static final FoodProperties MUSHROOM_PUREE = (new FoodProperties.Builder().nutrition(6).saturationMod(2f).fast().effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 80, 0), 1F).build());
    public static final FoodProperties PORK_PATE = (new FoodProperties.Builder().nutrition(7).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0), .7F).build());

    public static final FoodProperties BERRY_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(10).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 70, 0), 0.5F).build());
    public static final FoodProperties APPLE_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(10).saturationMod(7f).fast().effect(new MobEffectInstance(MobEffects.REGENERATION, 70, 0), 0.5F).build());
    public static final FoodProperties CACTUS_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(11).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.CONFUSION, 70, 0), 0.1F).build());
    public static final FoodProperties COCOA_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(7).saturationMod(7f).fast().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0), 0.6F).build());
    public static final FoodProperties MUSHROOM_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(10).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 110, 0), 0.4F).build());
    public static final FoodProperties PORK_SPREAD_TOAST = (new FoodProperties.Builder().nutrition(12).saturationMod(6f).fast().effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 70, 0), 0.1F).build());
 */
}
