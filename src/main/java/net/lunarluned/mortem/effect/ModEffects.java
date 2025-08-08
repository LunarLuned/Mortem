package net.lunarluned.mortem.effect;


import net.lunarluned.mortem.Mortem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static Holder<MobEffect> INFECTED;
//    public static MobEffect IMMUNE;
    public static Holder<MobEffect> IMMUNE;


    private static Holder<MobEffect> register(String name, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.tryBuild(Mortem.MOD_ID, name), mobEffect);
    }

    public static void registerEffects() {
        IMMUNE = register("immune", new ImmuneEffect(MobEffectCategory.BENEFICIAL, 14264576));
        INFECTED = register("infected", new InfectedEffect(MobEffectCategory.HARMFUL, 1657351));
    }
}
