package net.lunarluned.mortem.mixin.items;

import net.lunarluned.mortem.enchantments.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileWeaponItem.class)
public class InfinityReworkMixin {

    @Inject(method = "useAmmo", at = @At("HEAD"), cancellable = true)
    private static void mortem_infinityChance(
            ItemStack weapon, ItemStack projectile, LivingEntity holder, boolean forceInfinite, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!(holder instanceof Player player)) return;
        if (player.isCreative()) return;

        Registry<Enchantment> registry =
                player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        Holder<Enchantment> infinity = registry.getOrThrow(ModEnchantments.INFINITY);

        int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(infinity, weapon);
        if (enchLevel <= 0) return;

        float chance = switch (enchLevel) {
            case 1 -> 0.11f;
            case 2 -> 0.33f;
            case 3 -> 0.66f;
            default -> 0f;
        };

        if (player.getRandom().nextFloat() < chance) {
            cir.setReturnValue(projectile);
        }
    }
}