package net.lunarluned.mortem.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.lunarluned.mortem.MortemTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Repairable;

public class ModRepairMaterials {

    public static void registerRepairMaterials() {

            DefaultItemComponentEvents.MODIFY.register(context -> {

                setRepair(context, Items.BOW,
                        Items.STRING);

                setRepair(context, Items.CROSSBOW,
                        Items.STRING);

                setRepair(context, Items.TRIDENT,
                        Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS);

                setRepair(context, Items.FLINT_AND_STEEL,
                        Items.IRON_NUGGET);

                setRepair(context, Items.SHEARS,
                        Items.IRON_NUGGET);

                setRepair(context, Items.SHIELD,
                        Items.IRON_INGOT);

                setRepair(context, Items.SHEARS,
                        Items.IRON_NUGGET);

                setRepair(context, Items.FISHING_ROD,
                        Items.STRING);
                setRepair(context, Items.WARPED_FUNGUS_ON_A_STICK,
                        Items.STRING);
                setRepair(context, Items.CARROT_ON_A_STICK,
                        Items.STRING);
            });
        }

        private static void setRepair(DefaultItemComponentEvents.ModifyContext context,
                Item item,
                Item... repairItems) {

            context.modify(item, builder ->
                    builder.set(
                            net.minecraft.core.component.DataComponents.REPAIRABLE,
                            new Repairable(toHolderSet(repairItems))
                    )
            );
        }

        private static HolderSet<Item> toHolderSet(Item... items) {
            return HolderSet.direct(
                    java.util.Arrays.stream(items)
                            .map(BuiltInRegistries.ITEM::wrapAsHolder)
                            .toList()
            );
        }
    }