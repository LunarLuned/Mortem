package net.lunarluned.mortem.mixin.items;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ClearAllStatusEffectsConsumeEffect.class)
public class ClearAllStatusEffectsConsumeEffectMixin {

    @Inject(at = @At("HEAD"), method = "apply", cancellable = true)
    private void milkRework(Level level, ItemStack itemStack, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
            cir.setReturnValue(true);
    }
}