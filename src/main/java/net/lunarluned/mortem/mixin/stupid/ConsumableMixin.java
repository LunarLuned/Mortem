package net.lunarluned.mortem.mixin.stupid;

import net.fabricmc.loader.api.FabricLoader;
import net.lunarluned.mortem.Mortem;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Consumable.class)
public class ConsumableMixin {

    @Inject(method = "onConsume", at = @At("HEAD"))
    private void mortem_enigmaJamEffect(Level level, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (level.isClientSide() || !stack.is(ModItems.HAMMED_JAM)) return;
        if (!Mortem.IS_ENIGMA_INSTALLED) return;

        BuiltInRegistries.MOB_EFFECT
                .get(Identifier.fromNamespaceAndPath("moenigma", "disoriented"))
                .ifPresent(effect -> user.addEffect(new MobEffectInstance(effect, 34760, 0)));
    }
}