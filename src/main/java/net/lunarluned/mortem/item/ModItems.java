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
import net.minecraft.world.item.Items;

public class ModItems {

    public static final Item HEALING_VIAL = registerItem("healing_vial",
            new Item(new Item.Properties().food(ModFoods.DRUG, ModConsumables.DRUG)
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:healing_vial")))));

    public static final Item HOGLIN_TUSK = registerItem("hoglin_tusk",
            new Item(new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:hoglin_tusk")))));

    public static final Item EGG_BOWL = registerItem("egg_bowl",
            new Item(new Item.Properties().usingConvertsTo(Items.BOWL)
                    .useItemDescriptionPrefix().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:egg_bowl")))));

    public static final Item EGG_TUSK = registerItem("egg_tusk",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK)
                    .useItemDescriptionPrefix().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:egg_tusk")))));


    public static final Item SUSHI_ROLL = registerItem("sushi_roll",
            new Item(new Item.Properties().food(ModFoods.SUSHI_ROLL, ModConsumables.SUSHI_ROLL)
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:sushi_roll")))));

    public static final Item DRUG = registerItem("drug",
            new Item(new Item.Properties().food(ModFoods.DRUG, ModConsumables.DRUG)
                    .useItemDescriptionPrefix().stacksTo(99).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:drug")))));

    public static final Item RESIN_CANDY = registerItem("resin_candy",
            new Item(new Item.Properties().food(ModFoods.RESIN_CANDY, ModConsumables.RESIN_CANDY)
                    .useItemDescriptionPrefix().stacksTo(99).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:resin_candy")))));

    public static final Item HARD_BOILED_EGG = registerItem("hard_boiled_egg",
            new Item(new Item.Properties().food(ModFoods.HARD_BOILED_EGG)
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:hard_boiled_egg")))));

    public static final Item SCRAMBLED_EGGS = registerItem("scrambled_eggs",
            new Item(new Item.Properties().food(ModFoods.SCRAMBLED_EGGS).usingConvertsTo(Items.BOWL)
                    .useItemDescriptionPrefix().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:scrambled_eggs")))));

    public static final Item CRIMSON_STEW = registerItem("crimson_stew",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK).food(ModFoods.CRIMSON_STEW, ModConsumables.CRIMSON_STEW)
                    .useItemDescriptionPrefix().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:crimson_stew")))));

    public static final Item WARPED_STEW = registerItem("warped_stew",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK).food(ModFoods.WARPED_STEW, ModConsumables.WARPED_STEW)
                    .useItemDescriptionPrefix().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:warped_stew")))));

    public static final Item FUNGAL_STEW = registerItem("fungal_stew",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK).food(ModFoods.FUNGAL_STEW, ModConsumables.FUNGAL_STEW)
                    .useItemDescriptionPrefix().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:fungal_stew")))));

    public static final Item MUSHROOM_STEW_TUSK = registerItem("mushroom_stew_tusk",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK).food(ModFoods.MUSHROOM_STEW_TUSK, ModConsumables.MUSHROOM_STEW_TUSK)
                    .useItemDescriptionPrefix().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:mushroom_stew_tusk")))));

    public static final Item SCRAMBLED_EGGS_TUSK = registerItem("scrambled_eggs_tusk",
            new Item(new Item.Properties().usingConvertsTo(HOGLIN_TUSK).food(ModFoods.SCRAMBLED_EGGS_TUSK, ModConsumables.SCRAMBLED_EGGS_TUSK)
                    .useItemDescriptionPrefix().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("mortem:scrambled_eggs_tusk")))));



    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Mortem.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Mortem.LOGGER.info("Registering Mod Items for" + Mortem.MOD_ID);
    }
}
