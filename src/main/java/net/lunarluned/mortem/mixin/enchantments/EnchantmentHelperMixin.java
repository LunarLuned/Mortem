package net.lunarluned.mortem.mixin.enchantments;

import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Shadow
    public static void filterCompatibleEnchantments(List<EnchantmentInstance> possibleEntries, EnchantmentInstance pickedEntry) {
    }

    @Unique
    private static List<EnchantmentInstance> getPossibleEntries(int i, ItemStack itemStack, Stream<Holder<Enchantment>> stream) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        boolean bl = itemStack.is(Items.BOOK);
        stream.filter((holder) -> ((Enchantment)holder.value()).isPrimaryItem(itemStack) || bl).forEach((holder) -> {
            Enchantment enchantment = (Enchantment)holder.value();

            for(int j = enchantment.getMaxLevel(); j >= enchantment.getMinLevel(); --j) {
                if (i >= enchantment.getMinCost(j) && i <= enchantment.getMaxCost(j)) {
                    list.add(new EnchantmentInstance(holder, j));
                    break;
                }
            }
            list.removeIf(entry -> mortem_disallowedEnchantments(entry.enchantment().value().toString()));
        });
        return list;
    }

    @Inject(method = "selectEnchantment", at = @At("HEAD"), cancellable = true)
    private static void mortem_filterEnchantments(RandomSource randomSource, ItemStack itemStack, int level, Stream<Holder<Enchantment>> stream, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        Item item = itemStack.getItem();
        Enchantable enchantable = itemStack.get(DataComponents.ENCHANTABLE);
        if (enchantable == null) {
            cir.setReturnValue(list);
        } else {
            level += 1 + randomSource.nextInt(enchantable.value() / 4 + 1) + randomSource.nextInt(enchantable.value() / 4 + 1);
            float f = (randomSource.nextFloat() + randomSource.nextFloat() - 1.0F) * 0.15F;
            level = Mth.clamp(Math.round((float)level + (float)level * f), 1, Integer.MAX_VALUE);
            List<EnchantmentInstance> list2 = getPossibleEntries(level, itemStack, stream);
            if (!list2.isEmpty()) {
                WeightedRandom.getRandomItem(randomSource, list2, EnchantmentInstance::weight).ifPresent(list::add);
                while (randomSource.nextInt(50) <= level) {
                    if (!list.isEmpty()) {
                        filterCompatibleEnchantments(list2, Util.lastOf(list));
                    }

                    WeightedRandom.getRandomItem(randomSource, list2, EnchantmentInstance::weight).ifPresent(list::add);
                    level /= 2;
                }
            }

            cir.setReturnValue(list);
        }
    }

    @Unique
    private static boolean mortem_disallowedEnchantments(String enchantment) {
        List<String> invalidEnchants = List.of("Enchantment Mending", "Enchantment Flame");
        return invalidEnchants.contains(enchantment);
    }
}