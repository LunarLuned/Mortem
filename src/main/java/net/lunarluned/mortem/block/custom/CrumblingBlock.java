package net.lunarluned.mortem.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CrumblingBlock extends Block {
    public CrumblingBlock(Properties settings) {
        super(settings);
    }
//&& player.getRandom().nextInt(100) <= Peculia.getConfig().items.itemsConfig.itemChances.echoing_chance)
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
            if (entity instanceof Player) {
                setToAir(state, level, pos);
                this.spawnDestroyParticles(level, (Player) entity, pos, state);
                entity.gameEvent(GameEvent.BLOCK_DESTROY);
                level.playSound(null, pos, SoundEvents.NETHERRACK_BREAK, SoundSource.BLOCKS, 1F, 1.0F);
                level.levelEvent(null, 2001, pos, getId(state));
                super.stepOn(level, pos, state, entity);
            }
    }

    public static void setToAir(BlockState state, Level world, BlockPos pos) {
        world.setBlockAndUpdate(pos, pushEntitiesUp(state, Blocks.AIR.defaultBlockState(), world, pos));
    }
}
