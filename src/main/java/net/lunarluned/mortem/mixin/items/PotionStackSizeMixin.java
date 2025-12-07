package net.lunarluned.mortem.mixin.items;

import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;


@Mixin(PotionItem.class)
public class PotionStackSizeMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Item.Properties mortem_modifyStackSize(Item.Properties value) {
        return value.stacksTo(8);
    }
}