package net.lunarluned.mortem.potion;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotions {

    public static Holder<Potion> INFECTED;
    public static Holder<Potion> DEEP_INFECTION;
    public static Holder<Potion> IMMUNE;
    public static Holder<Potion> EXTENDED_IMMUNITY;

    private static Holder<Potion> register(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, ResourceLocation.tryBuild(Mortem.MOD_ID, name), potion);
    }

    public static void registerPotions() {
        INFECTED = register("infected",
                new Potion("infected", new MobEffectInstance(ModEffects.INFECTED, 12000, 0)));

        DEEP_INFECTION = register("deep_infection",
                new Potion("deep_infection", new MobEffectInstance(ModEffects.INFECTED, 6000, 1)));

        IMMUNE = register("immune",
                new Potion("immune", new MobEffectInstance(ModEffects.IMMUNE, 4000, 0)));

        EXTENDED_IMMUNITY = register("extended_immunity",
                new Potion("extended_immunity", new MobEffectInstance(ModEffects.IMMUNE, 8000, 0)));

        Mortem.LOGGER.info("Registering Potions for " + Mortem.MOD_ID);

    }
}