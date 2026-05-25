package net.lunarluned.mortem.mixin.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class DurabilityItemStackMixin {

    @Inject(
            method = "getTooltipLines",
            at = @At("RETURN")
    )
    private void mortem_addDurabilityTooltip(
            Item.TooltipContext context, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir
    ) {
        ItemStack stack = (ItemStack)(Object)this;

        if (!stack.isDamageableItem()) {
            return;
        }

        int maxDurability = stack.getMaxDamage();
        int remaining = maxDurability - stack.getDamageValue();

        List<Component> tooltip = cir.getReturnValue();
        tooltip.add(Component.empty());
        tooltip.add(
                Component.literal(
                        "Durability: ").withStyle(ChatFormatting.GRAY).append(Component.literal( remaining + " / " + maxDurability).withStyle(ChatFormatting.WHITE))
        );
    }
}