package net.lunarluned.mortem.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedMagmaBlock extends MagmaBlock {
    public ReinforcedMagmaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(final Level level, final BlockPos pos, final BlockState onState, final Entity entity) {
    }
}
