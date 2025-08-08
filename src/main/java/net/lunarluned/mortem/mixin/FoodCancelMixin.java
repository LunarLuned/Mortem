package net.lunarluned.mortem.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class FoodCancelMixin extends LivingEntity {
    @Shadow public abstract ItemCooldowns getCooldowns();

    protected FoodCancelMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void mortem_tick(CallbackInfo ci) {
        if (this.hurtTime > 0 && this.isUsingItem()) {
            if (this.getUseItem().getItem() != Items.SHIELD) {
                this.stopUsingItem();
                this.getCooldowns().addCooldown(this.getUseItem(), 20);
            }
        }
    }
}
