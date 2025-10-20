package net.lunarluned.mortem.block.custom;

import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import static net.lunarluned.mortem.block.ModBlocks.HARDENED_DEEPSLATE_IRON_ORE;

public class HardenedIronOre extends Block {
    public HardenedIronOre(Properties properties) {
        super(properties);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        ItemStack tool = builder.getOptionalParameter(LootContextParams.TOOL);

        if (tool != null) {
            int miningLevel = tool.getMaxDamage();
            if (miningLevel < 130) {
                return Collections.emptyList();
            } else if (miningLevel == 190) {
                return Collections.singletonList(new ItemStack(Items.IRON_NUGGET, Mth.nextInt(RandomSource.create(), 3, 6)));
            }
            else if (miningLevel > 200) {
                return Collections.singletonList(new ItemStack(Items.RAW_IRON, 1));
            } else {
                return Collections.singletonList(new ItemStack(Items.IRON_NUGGET, Mth.nextInt(RandomSource.create(), 1, 2)));
            }
        }

        return Collections.emptyList();
    }

    @Override
    protected void onExplosionHit(BlockState blockState, ServerLevel level, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> biConsumer) {
        if (this.defaultBlockState().is(HARDENED_DEEPSLATE_IRON_ORE)) {
        level.setBlock(blockPos, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), 3);
        } else {
        level.setBlock(blockPos, Blocks.IRON_ORE.defaultBlockState(), 3);
        }
        level.playSound(null, blockPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 2.0F, 0.25F);
        BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos) : null;
        LootParams.Builder builder = (new LootParams.Builder(level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity).withOptionalParameter(LootContextParams.THIS_ENTITY, explosion.getDirectSourceEntity());
        blockState.getDrops(builder).forEach((itemStack) -> biConsumer.accept(itemStack, blockPos));
    }
}
