package net.lunarluned.mortem.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(WebBlock.class)
public class CobwebMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;makeStuckInBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/Vec3;)V"), method = "entityInside")
    public void mortemEntityInside(Entity instance, BlockState blockState, Vec3 vec3) {
        instance.makeStuckInBlock(blockState, new Vec3(0.65, 0.25000000074505806, 0.65));
    }

    private static final Map<UUID, Integer> entityCobwebTimer = new HashMap<>();

    @Inject(method = "entityInside", at = @At("TAIL"))
    public void mortemCobwebBreak(BlockState blockState, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, CallbackInfo ci) {
        if (!level.isClientSide() && entity instanceof LivingEntity) {
            UUID entityUUID = entity.getUUID();
            int timeInCobweb = entityCobwebTimer.getOrDefault(entityUUID, 0) + 1;

            int threshold = 60;

            if (timeInCobweb >= threshold) {
                level.destroyBlock(pos, false);
                ItemStack stringStack = new ItemStack(Items.STRING);
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.1, pos.getY() + 0.1, pos.getZ() + 0.1, stringStack));
                entityCobwebTimer.remove(entityUUID);
            } else {
                entityCobwebTimer.put(entityUUID, timeInCobweb);
            }
        }
    }

    @Inject(method = "entityInside", at = @At("TAIL"))
    public void mortemCobwebBreakReset(BlockState blockState, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, CallbackInfo ci) {
        // Reset the timer if the entity leaves the cobweb
        if (!level.isClientSide() && !(entity.blockPosition().equals(pos))) {
            entityCobwebTimer.remove(entity.getId());
        }
    }
}
