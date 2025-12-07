package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin {

    @Inject(
            method = "populateDefaultEquipmentSlots",
            at = @At("TAIL")
    )
    private void modifySkeletonEquipment(RandomSource randomSource, DifficultyInstance difficultyInstance, CallbackInfo ci) {
        AbstractSkeleton skeleton = (AbstractSkeleton)(Object)this;
            // 30% chance to get a bow, bone or nothing
            if (randomSource.nextFloat() < 0.30f) {
                skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE));
            } else if (randomSource.nextFloat() < 0.30f) {
                skeleton.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            } else if (randomSource.nextFloat() < 0.30f) {
                skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
    }
}