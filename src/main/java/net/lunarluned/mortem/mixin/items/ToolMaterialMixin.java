package net.lunarluned.mortem.mixin.items;

import com.google.common.collect.Maps;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {


    @Mutable
    @Shadow
    @Final
    public static ToolMaterial GOLD;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mortem_changeToolValues(CallbackInfo ci) {
        GOLD = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL, 225, 15.0F, 2.0F, 22, ItemTags.GOLD_TOOL_MATERIALS);
    }
}