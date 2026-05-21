package net.lunarluned.mortem.mixin.entities.passive;

import net.lunarluned.mortem.item.ModItems;
import net.lunarluned.mortem.world.entity.ai.goal.EatCropGoal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.UseItemGoal;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTrader.class)
public class WanderingTraderMixin {


    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void onRegisterGoals(CallbackInfo ci) {
        WanderingTrader self = (WanderingTrader) (Object) this;
        self.targetSelector.addGoal(0, new UseItemGoal(self, new ItemStack(ModItems.ENHANCED_MISFORTUNE_ELIXIR), SoundEvents.WANDERING_TRADER_REAPPEARED, (e) -> self.level().isBrightOutside() && self.isInvisible()));
        self.targetSelector.removeGoal(new UseItemGoal(self, new ItemStack(Items.MILK_BUCKET), SoundEvents.WANDERING_TRADER_REAPPEARED, (e) -> self.level().isBrightOutside() && self.isInvisible()));
    }

}
