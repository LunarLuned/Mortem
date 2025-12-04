package net.lunarluned.mortem.enchantments;

import net.lunarluned.mortem.Mortem;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> RICOCHET =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(Mortem.MOD_ID, "ricochet"));

    public static final ResourceKey<Enchantment> CHINING =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(Mortem.MOD_ID, "chining"));

    public static final ResourceKey<Enchantment> HARVESTING =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(Mortem.MOD_ID, "harvesting"));

    public static void bootstrap(BootstrapContext<Enchantment> registerable) {
        var enchantments = registerable.lookup(Registries.ENCHANTMENT);
        var items = registerable.lookup(Registries.ITEM);
    }


    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}