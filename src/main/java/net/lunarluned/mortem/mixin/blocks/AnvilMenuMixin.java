package net.lunarluned.mortem.mixin.blocks;


import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {


    @Shadow
    @Final
    private DataSlot cost;

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void mortem_mayPickup(Player player, boolean present, CallbackInfoReturnable<Boolean> cir) {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);

        boolean hasBook = right.getItem() == Items.ENCHANTED_BOOK;
        boolean enchantedLeft = !EnchantmentHelper.getEnchantmentsForCrafting(left).isEmpty();
        boolean enchantedRight = !EnchantmentHelper.getEnchantmentsForCrafting(right).isEmpty();
        if (!hasBook && !enchantedLeft && !enchantedRight) {
            cost.set(0);
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue((player.hasInfiniteMaterials() || player.experienceLevel >= this.cost.get()) && this.cost.get() > 0);
        }
    }

    @Inject(method = "createResult", at = @At("TAIL"), cancellable = true)
    private void mortem_createResult(CallbackInfo ci) {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);

        boolean hasBook = right.getItem() == Items.ENCHANTED_BOOK;
        boolean enchantedLeft = !EnchantmentHelper.getEnchantmentsForCrafting(left).isEmpty();
        boolean enchantedRight = !EnchantmentHelper.getEnchantmentsForCrafting(right).isEmpty();

        // Only consume XP if enchanted items/books are involved
        if (!hasBook && !enchantedLeft && !enchantedRight) {
            cost.set(0);
            }
        }

    /**
     * @author lunarluned
     * @reason removed the increasing cost of repairing
     */
    @Overwrite
    public static int calculateIncreasedRepairCost(int cost) {
        return cost;
    }

}
