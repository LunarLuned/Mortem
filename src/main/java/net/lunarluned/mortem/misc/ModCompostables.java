package net.lunarluned.mortem.misc;

import net.lunarluned.mortem.item.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;

public class ModCompostables {

    public static void registerCompostables() {

        ComposterBlock.COMPOSTABLES.put(Items.ROTTEN_FLESH, 0.4F);

        ComposterBlock.COMPOSTABLES.put(ModItems.DRUG, 0.1F);

        ComposterBlock.COMPOSTABLES.put(ModItems.RAW_PUMPKIN_GUTS, 1.0F);
        ComposterBlock.COMPOSTABLES.put(ModItems.BAKED_PUMPKIN_GUTS, 1.0F);

        ComposterBlock.COMPOSTABLES.put(ModItems.BAKED_APPLE, 0.5F);

        ComposterBlock.COMPOSTABLES.put(ModItems.POTATO_WEDGE, 1.0F);
        ComposterBlock.COMPOSTABLES.put(ModItems.BAKED_POTATO_WEDGE, 1.0F);

        ComposterBlock.COMPOSTABLES.put(ModItems.RAW_MUTTON_SLICE, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.COOKED_MUTTON_SLICE, 0.9F);
        ComposterBlock.COMPOSTABLES.put(ModItems.RAW_CHICKEN_NUGGET, 0.4F);
        ComposterBlock.COMPOSTABLES.put(ModItems.COOKED_CHICKEN_NUGGET, 0.8F);

        ComposterBlock.COMPOSTABLES.put(ModItems.SCRAMBLED_EGGS_TUSK, 0.25F);
        ComposterBlock.COMPOSTABLES.put(ModItems.SCRAMBLED_EGGS, 0.25F);

        ComposterBlock.COMPOSTABLES.put(ModItems.SUSHI_ROLL, 0.5F);

        ComposterBlock.COMPOSTABLES.put(ModItems.RAW_BACON, 0.35F);
        ComposterBlock.COMPOSTABLES.put(ModItems.COOKED_BACON, 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.BEEF_PATTY, 0.35F);
        ComposterBlock.COMPOSTABLES.put(ModItems.COOKED_BEEF_PATTY, 0.45F);

        ComposterBlock.COMPOSTABLES.put(ModItems.BREAKFAST_SANDWICH, 0.75F);
        ComposterBlock.COMPOSTABLES.put(ModItems.BURGER, 1.0F);
        ComposterBlock.COMPOSTABLES.put(ModItems.BACON_BURGER, 1.0F);

        ComposterBlock.COMPOSTABLES.put(ModItems.TOAST, 0.45F);
        ComposterBlock.COMPOSTABLES.put(ModItems.TOASTED_BREAD, 0.5F);

        ComposterBlock.COMPOSTABLES.put(ModItems.CACTUS_SPREAD_TOAST, 1.0F);
        ComposterBlock.COMPOSTABLES.put(ModItems.MUSHROOM_SPREAD_TOAST, 1.0F);
        ComposterBlock.COMPOSTABLES.put(ModItems.PORK_SPREAD_TOAST, 1.0F);
    }
}