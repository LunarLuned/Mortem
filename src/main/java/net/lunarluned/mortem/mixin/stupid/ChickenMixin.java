package net.lunarluned.mortem.mixin.stupid;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chicken.class)
public abstract class ChickenMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mortem_modifyScaleInsideJoke(EntityType type, Level level, CallbackInfo ci) {
        // #InsideJoke
        Chicken self = (Chicken) (Object) this;

        if (self.getRandom().nextInt(1000) > 995) {
            AttributeInstance range = self.getAttribute(Attributes.SCALE);
            if (range != null) {
                range.setBaseValue(4.0);
            }
        }
    }
}