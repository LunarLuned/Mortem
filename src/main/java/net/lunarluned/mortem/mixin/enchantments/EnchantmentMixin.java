package net.lunarluned.mortem.mixin.enchantments;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow
    public abstract String toString();

    @Inject(method = "isSupportedItem", at = @At("HEAD"), cancellable = true)
    private void disableDisallowedEnchantments(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (mortem_disallowedEnchantments(((Enchantment) (Object) this).toString())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "areCompatible", at = @At("HEAD"), cancellable = true)
    private static void disableDisallowedCombination(Holder<Enchantment> holder, Holder<Enchantment> holder2, CallbackInfoReturnable<Boolean> cir) {
        if (mortem_disallowedEnchantments(holder.value().toString()) || mortem_disallowedEnchantments(holder2.value().toString())) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private static boolean mortem_disallowedEnchantments(String enchantment) {
        List<String> invalidEnchants = List.of("Enchantment Mending", "Enchantment Flame");
        return invalidEnchants.contains(enchantment);
    }
}