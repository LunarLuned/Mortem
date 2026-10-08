package net.lunarluned.mortem.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Properties;

@Mixin(HugeMushroomBlock.class)
public class HugeMushroomBlockMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static BlockBehaviour.Properties bouncy(BlockBehaviour.Properties properties) {
        return properties.bounceRestitution(1.0F);
    }
}