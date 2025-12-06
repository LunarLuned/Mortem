package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.RecycleResult;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemEntity.class)
public abstract class LavaSmeltingMixin {
    @Unique
    private static final Map<Item, RecycleResult> LAVA_RECIPES = new HashMap<>();

@Inject(method = "tick", at = @At("HEAD"))
private void onTick(CallbackInfo ci) {
    ItemEntity entity = (ItemEntity) (Object) this;
    Level level = entity.level();

    if (level.isClientSide()) return;

    BlockPos pos = entity.blockPosition();
    ItemStack stack = entity.getItem();
    Item item = stack.getItem();

    // If in lava and has a recycle recipe
    if (level.getFluidState(pos).is(FluidTags.LAVA) && LAVA_RECIPES.containsKey(item)) {
        if (level.dimension() == Level.NETHER) {
            RecycleResult recipe = LAVA_RECIPES.get(item);

            int count = stack.getCount() * recipe.count();
            ItemStack result = new ItemStack(recipe.result(), count);

            // Spawn new item(s) above lava
            ItemEntity newEntity = new ItemEntity(
                    level,
                    entity.getX(),
                    entity.getY() + 0.6,
                    entity.getZ(),
                    result
            );
            level.addFreshEntity(newEntity);

            // Particles & sounds
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.LAVA,
                        entity.getX(),
                        entity.getY() + 0.3,
                        entity.getZ(),
                        10,
                        0.2, 0.2, 0.2, 0.01
                );
                serverLevel.levelEvent(2001, pos, net.minecraft.world.level.block.Block.getId(level.getBlockState(pos)));
                level.playSound(
                        null,
                        pos,
                        net.minecraft.sounds.SoundEvents.LAVA_EXTINGUISH,
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );
            }

        entity.discard();
        }
    }
}

    static {
        // Gold items
        LAVA_RECIPES.put(Items.GOLDEN_HELMET, new RecycleResult(Items.GOLD_INGOT, 1));
        LAVA_RECIPES.put(Items.GOLDEN_CHESTPLATE, new RecycleResult(Items.GOLD_INGOT, 3));
        LAVA_RECIPES.put(Items.GOLDEN_LEGGINGS, new RecycleResult(Items.GOLD_INGOT, 2));
        LAVA_RECIPES.put(Items.GOLDEN_BOOTS, new RecycleResult(Items.GOLD_INGOT, 1));
        LAVA_RECIPES.put(Items.GOLDEN_SWORD, new RecycleResult(Items.GOLD_NUGGET, 7));
        LAVA_RECIPES.put(Items.GOLDEN_AXE, new RecycleResult(Items.GOLD_NUGGET, 8));
        LAVA_RECIPES.put(Items.GOLDEN_PICKAXE, new RecycleResult(Items.GOLD_NUGGET, 7));
        LAVA_RECIPES.put(Items.GOLDEN_SHOVEL, new RecycleResult(Items.GOLD_NUGGET, 5));
        LAVA_RECIPES.put(Items.GOLDEN_HOE, new RecycleResult(Items.GOLD_NUGGET, 12));

        LAVA_RECIPES.put(Items.RAW_GOLD_BLOCK, new RecycleResult(Items.GOLD_INGOT, 5));

        // Copper items
        LAVA_RECIPES.put(Items.COPPER_HELMET, new RecycleResult(Items.COPPER_INGOT, 1));
        LAVA_RECIPES.put(Items.COPPER_CHESTPLATE, new RecycleResult(Items.COPPER_INGOT, 3));
        LAVA_RECIPES.put(Items.COPPER_LEGGINGS, new RecycleResult(Items.COPPER_INGOT, 2));
        LAVA_RECIPES.put(Items.COPPER_BOOTS, new RecycleResult(Items.COPPER_INGOT, 1));
        LAVA_RECIPES.put(Items.COPPER_SWORD, new RecycleResult(Items.COPPER_NUGGET, 7));
        LAVA_RECIPES.put(Items.COPPER_AXE, new RecycleResult(Items.COPPER_NUGGET, 8));
        LAVA_RECIPES.put(Items.COPPER_PICKAXE, new RecycleResult(Items.COPPER_NUGGET, 7));
        LAVA_RECIPES.put(Items.COPPER_SHOVEL, new RecycleResult(Items.COPPER_NUGGET, 5));
        LAVA_RECIPES.put(Items.COPPER_HOE, new RecycleResult(Items.COPPER_NUGGET, 12));

        LAVA_RECIPES.put(Item.byBlock(ModBlocks.COPPER_RAIL), new RecycleResult(Items.COPPER_NUGGET, 2));
        LAVA_RECIPES.put(Items.RAW_COPPER_BLOCK, new RecycleResult(Items.COPPER_INGOT, 6));

        // Iron items
        LAVA_RECIPES.put(Items.IRON_HELMET, new RecycleResult(Items.IRON_INGOT, 2));
        LAVA_RECIPES.put(Items.IRON_CHESTPLATE, new RecycleResult(Items.IRON_INGOT, 4));
        LAVA_RECIPES.put(Items.IRON_LEGGINGS, new RecycleResult(Items.IRON_INGOT, 3));
        LAVA_RECIPES.put(Items.IRON_BOOTS, new RecycleResult(Items.IRON_INGOT, 2));

        LAVA_RECIPES.put(Items.IRON_SWORD, new RecycleResult(Items.IRON_NUGGET, 14));
        LAVA_RECIPES.put(Items.IRON_AXE, new RecycleResult(Items.IRON_INGOT, 1));
        LAVA_RECIPES.put(Items.IRON_PICKAXE, new RecycleResult(Items.IRON_INGOT, 1));
        LAVA_RECIPES.put(Items.IRON_SHOVEL, new RecycleResult(Items.IRON_NUGGET, 5));
        LAVA_RECIPES.put(Items.IRON_HOE, new RecycleResult(Items.IRON_NUGGET, 10));

        LAVA_RECIPES.put(Items.RAW_IRON_BLOCK, new RecycleResult(Items.IRON_INGOT, 7));

        LAVA_RECIPES.put(Items.BUCKET, new RecycleResult(Items.IRON_INGOT, 2));
        LAVA_RECIPES.put(Items.MINECART, new RecycleResult(Items.IRON_INGOT, 4));
        LAVA_RECIPES.put(Items.RAIL, new RecycleResult(Items.IRON_NUGGET, 1));
        LAVA_RECIPES.put(Items.FLINT_AND_STEEL, new RecycleResult(Items.IRON_NUGGET, 5));
        LAVA_RECIPES.put(Items.SHEARS, new RecycleResult(Items.IRON_NUGGET, 15));
        LAVA_RECIPES.put(Items.COMPASS, new RecycleResult(Items.IRON_INGOT, 2));
        LAVA_RECIPES.put(Items.IRON_DOOR, new RecycleResult(Items.IRON_INGOT, 2));
        LAVA_RECIPES.put(Items.IRON_TRAPDOOR, new RecycleResult(Items.IRON_INGOT, 2));
    }
}
