package net.lunarluned.mortem.potion;


import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotions {


    public static final Potion INFECTED = registerPotion("infected",
            new Potion("infected", new MobEffectInstance(ModEffects.INFECTED, 12000, 0)));

    public static final Potion IMMUNE = registerPotion("immune",
            new Potion("immune", new MobEffectInstance(ModEffects.IMMUNE, 6000, 0)));



    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(BuiltInRegistries.POTION, ResourceLocation.tryBuild(Mortem.MOD_ID, name), potion);
    }


    public static void registerPotions() {
        Mortem.LOGGER.info("Registering Mortem Potions");

    }
}