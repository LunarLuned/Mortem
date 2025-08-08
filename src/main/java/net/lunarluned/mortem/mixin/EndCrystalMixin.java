package net.lunarluned.mortem.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(EndCrystalItem.class)
public class EndCrystalMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void mortemUseOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        // i aint gonna hold yall i just grabbed the old code and removed the obsidian functionality. im sure theres a cleaner way of doing this but I DONT CARE
        // (ill probably do it later)
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (!blockState.is(Blocks.BEDROCK)) {
            cir.setReturnValue(InteractionResult.FAIL);
        } else {
            BlockPos blockPos2 = blockPos.above();
            if (!level.isEmptyBlock(blockPos2)) {
                cir.getReturnValue();
            } else {
                double d = (double)blockPos2.getX();
                double e = (double)blockPos2.getY();
                double f = (double)blockPos2.getZ();
                List<Entity> list = level.getEntities((Entity)null, new AABB(d, e, f, d + (double)1.0F, e + (double)2.0F, f + (double)1.0F));
                if (!list.isEmpty()) {
                    cir.setReturnValue(InteractionResult.FAIL);
                } else {
                    if (level instanceof ServerLevel) {
                        EndCrystal endCrystal = new EndCrystal(level, d + (double)0.5F, e, f + (double)0.5F);
                        endCrystal.setShowBottom(false);
                        level.addFreshEntity(endCrystal);
                        level.gameEvent(useOnContext.getPlayer(), GameEvent.ENTITY_PLACE, blockPos2);
                        EndDragonFight endDragonFight = ((ServerLevel)level).getDragonFight();
                        if (endDragonFight != null) {
                            endDragonFight.tryRespawn();
                        }
                    }
                    useOnContext.getItemInHand().shrink(1);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
