package net.lunarluned.mortem.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends AbstractGolem {


    protected IronGolemMixin(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }
        // Decreased the Iron Golem's health from 100 to 50, increases Armor to 15

        @Inject(at = @At("HEAD"), method = "createAttributes", cancellable = true)
        private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
                cir.setReturnValue(Mob.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 50)
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.KNOCKBACK_RESISTANCE, (double)1.0F)
                        .add(Attributes.ATTACK_DAMAGE, (double)15.0F)
                        .add(Attributes.STEP_HEIGHT, (double)1.0F)
                        .add(Attributes.ARMOR, 15.0));
        }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mortemMobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {

        // Made Iron Nuggets also repair Iron Golems (1 Heart), nerfed Iron Ingots to only heal a row of health considering the health change

        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.IRON_INGOT) || itemStack.is(Items.IRON_NUGGET)) {
            float f = this.getHealth();
            if (itemStack.is(Items.IRON_NUGGET)) {
                this.heal(2.0f);
            } else if (itemStack.is(Items.IRON_INGOT)) {
                this.heal(20.0F);
            }
            if (this.getHealth() == f) {
                cir.setReturnValue(InteractionResult.PASS);
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, g);
                itemStack.consume(1, player);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        } else {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
