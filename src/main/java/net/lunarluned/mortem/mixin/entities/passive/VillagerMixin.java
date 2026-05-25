package net.lunarluned.mortem.mixin.entities.passive;


import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    @Inject(
            at = @At("RETURN"),
            method = "updateTrades"
    )
    private void modifyAllBadBookTrades(CallbackInfo ci) {
        MerchantOffers merchantOffers = this.getOffers();

        if (merchantOffers == null) return;

        Registry<Enchantment> registry =
                this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        Holder<Enchantment> mending = registry.getOrThrow(Enchantments.MENDING);
        Holder<Enchantment> flame = registry.getOrThrow(Enchantments.FLAME);
        Holder<Enchantment> infinity = registry.getOrThrow(Enchantments.INFINITY);

        for (int i = 0; i < merchantOffers.size(); ++i) {
            MerchantOffer offer = merchantOffers.get(i);

            ItemStack item = offer.getResult();

            if (!item.is(Items.ENCHANTED_BOOK)) continue;

            ItemEnchantments enchantments =
                    item.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

            boolean hasBlockedEnchant =
                    enchantments.keySet().contains(mending) ||
                            enchantments.keySet().contains(flame) ||
                            enchantments.keySet().contains(infinity);

            if (!hasBlockedEnchant) continue;

            // Replace with a normal book (no enchants, no components)
            ItemStack replacement = new ItemStack(Items.BOOK);

            MerchantOffer newOffer = new MerchantOffer(
                    offer.getItemCostA(),
                    replacement,
                    offer.getMaxUses(),
                    offer.getXp(),
                    offer.getPriceMultiplier()
            );

            merchantOffers.set(i, newOffer);
        }
    }
}
