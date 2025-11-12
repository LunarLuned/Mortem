package net.lunarluned.mortem.enchantments;

import com.mojang.serialization.MapCodec;
import net.lunarluned.mortem.Mortem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class ModEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> RICOCHET =
            registerEntityEffect("ricochet", RicochetEnchantmentEffect.CODEC);


    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ResourceLocation.tryBuild(Mortem.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        Mortem.LOGGER.info("Registering Mod Enchantment Effects for " + Mortem.MOD_ID);
    }
}