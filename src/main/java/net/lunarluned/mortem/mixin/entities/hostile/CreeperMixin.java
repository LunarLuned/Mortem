package net.lunarluned.mortem.mixin.entities.hostile;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Creeper.class)
public abstract class CreeperMixin {

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void modifyFollowRange(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();

        builder.add(Attributes.FOLLOW_RANGE, 20);

        cir.setReturnValue(builder);
    }
}
