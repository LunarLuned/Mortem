package net.lunarluned.mortem.enchantments;

import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> RICOCHET =
            ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Mortem.MOD_ID, "ricochet"));

    public static final ResourceKey<Enchantment> CHINING =
            ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Mortem.MOD_ID, "chining"));

    public static final ResourceKey<Enchantment> HARVESTING =
            ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Mortem.MOD_ID, "harvesting"));

    public static final ResourceKey<Enchantment> INFINITY =
            ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Mortem.MOD_ID, "infinity"));


    public static void bootstrap(BootstrapContext<Enchantment> context) {

        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        register(
                context,
                RICOCHET,
                Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                                15, // weight
                                1,  // max level
                                Enchantment.dynamicCost(1, 10),   // min cost
                                Enchantment.dynamicCost(16, 30),  // max cost
                                2,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );

        register(
                context,
                INFINITY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(MortemTags.BOW_AND_CROSSBOW_ENCHANTABLES),
                                15, // weight
                                3,  // max level
                                Enchantment.dynamicCost(1, 10),   // min cost
                                Enchantment.dynamicCost(16, 30),  // max cost
                                2,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );

        register(
                context,
                CHINING,
                Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.HOES),
                                5,
                                1,
                                Enchantment.dynamicCost(5, 8),
                                Enchantment.dynamicCost(20, 8),
                                2,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );

        register(
                context,
                HARVESTING,
                Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.HOES),
                                8,
                                3,
                                Enchantment.dynamicCost(1, 10),
                                Enchantment.dynamicCost(15, 10),
                                2,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );
    }


    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.identifier()));
    }
}