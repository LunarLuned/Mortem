package net.lunarluned.mortem.mixin.items;

import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mortem_changeArmorValues(CallbackInfo ci) {
        ArmorMaterials.DIAMOND.defense().put(ArmorType.LEGGINGS, 5);
        ArmorMaterials.DIAMOND.defense().put(ArmorType.CHESTPLATE, 7);
        ArmorMaterials.LEATHER.defense().put(ArmorType.LEGGINGS, 3);
        ArmorMaterials.IRON.defense().put(ArmorType.CHESTPLATE, 5);
        ArmorMaterials.GOLD.defense().put(ArmorType.LEGGINGS, 4);
        ArmorMaterials.GOLD.defense().put(ArmorType.BOOTS, 2);
    }
}