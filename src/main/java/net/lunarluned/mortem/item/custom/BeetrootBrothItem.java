package net.lunarluned.mortem.item.custom;

import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BeetrootBrothItem extends Item {

    public BeetrootBrothItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(Blocks.CAULDRON)) {

            if (!level.isClientSide()) {

                //level.setBlock(
                      //  pos,
                    //    ModBlocks.HOTPOT_CAULDRON.defaultBlockState(),
                  //      3
                //);

                if (!context.getPlayer().getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                    context.getPlayer().addItem(new ItemStack(Items.BUCKET));
                }
            }

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
}