package net.lunarluned.mortem.block.custom.crops;

import net.lunarluned.mortem.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.NonNull;

public class RhyneappleCropBlock extends NetherCropBlock {
    public RhyneappleCropBlock(Properties properties) {
        super(properties);
    }

    public static final int MAX_AGE = 4;

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    @Override
    protected @NonNull ItemLike getBaseSeedId() {
        return ModItems.RHYNEAPPLE_CROWN;
    }

    @Override
    protected @NonNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
