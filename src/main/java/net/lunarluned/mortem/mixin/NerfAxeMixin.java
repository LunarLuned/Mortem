package net.lunarluned.mortem.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Item.Properties.class)
public abstract class NerfAxeMixin {

    @ModifyVariable(method = "axe", at = @At("HEAD"), argsOnly = true, name = "attackDamageBaseline")
    private float mortem_nerfAxeDamage(float damage, ToolMaterial material) {
        if (material == ToolMaterial.WOOD) return damage - 2.0F;
        if (material == ToolMaterial.COPPER || material == ToolMaterial.STONE) return damage - 3.0F;
        if (material == ToolMaterial.GOLD || material == ToolMaterial.IRON) return damage - 2.5F;
        return damage;
    }
}