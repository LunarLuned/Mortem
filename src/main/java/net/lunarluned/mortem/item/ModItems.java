package net.lunarluned.mortem.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lunarluned.mortem.Mortem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item LESSER_HEALTH_POTION = registerItem("lesser_health_potion",
            new Item(new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:lesser_health_potion")))));

    public static final Item HEALTH_POTION = registerItem("health_potion",
            new Item(new Item.Properties()
            .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:health_potion")))));

    public static final Item GREATER_HEALTH_POTION = registerItem("greater_health_potion",
            new Item(new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:greater_health_potion")))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Mortem.LOGGER.info("Registering Mod Items for" + Mortem.MOD_ID);
    }
}
