package net.lunarluned.mortem.mixin.enchantments;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class EnchantBookFactoryMixin {
    @Shadow
    @Final
    private TagKey<Enchantment> tradeableEnchantments;

    @Shadow @Final private int minLevel;

    @Shadow @Final private int maxLevel;

    @Shadow @Final private int villagerXp;

    // Mending & Flame are unobtainable

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void mortem_removeEnchantsFromTradeOffers(Entity entity, RandomSource randomSource, CallbackInfoReturnable<MerchantOffer> cir) {
            Optional<Holder<Enchantment>> optional = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getRandomElementOf(this.tradeableEnchantments, randomSource);
            int l;
            ItemStack itemStack;
            if (optional.isPresent()) {
                Holder<Enchantment> holder = optional.get();
                Enchantment enchantment = holder.value();
                int i = Math.max(enchantment.getMinLevel(), this.minLevel);
                int j = Math.min(enchantment.getMaxLevel(), this.maxLevel);
                int k = Mth.nextInt(randomSource, i, j);
                itemStack = EnchantmentHelper.createBook(new EnchantmentInstance(holder, k));

            if (mortem_disallowedEnchantments(enchantment.toString())) {
                itemStack = Items.EXPERIENCE_BOTTLE.getDefaultInstance();
            }
                l = 2 + randomSource.nextInt(5 + k * 10) + 3 * k;
                if (holder.is(EnchantmentTags.DOUBLE_TRADE_PRICE)) {
                    l *= 2;
                }

                if (l > 64) {
                    l = 64;
                }
        } else {
            l = 1;
            itemStack = new ItemStack(Items.BOOK);
        }

        if (itemStack.getItem() == Items.EXPERIENCE_BOTTLE) {
            itemStack.setCount(6);
            cir.setReturnValue(new MerchantOffer(new ItemCost(Items.EMERALD, 1), itemStack, 12, this.villagerXp, 0.2F));
        } else {
            cir.setReturnValue(new MerchantOffer(new ItemCost(Items.EMERALD, l), Optional.of(new ItemCost(Items.BOOK, 1)), itemStack, 12, this.villagerXp, 0.2F));
        }
    }

    @Unique
    private static boolean mortem_disallowedEnchantments(String enchantment) {
        List<String> invalidEnchants = List.of("Enchantment Flame", "Enchantment Mending");
        return invalidEnchants.contains(enchantment);
    }
}