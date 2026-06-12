package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.RecycleResult;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemEntity.class)
public abstract class SawTableCraftingMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Unique
    private static final Map<Item, RecycleResult> SAW_TABLE_RECIPES = new HashMap<>();

@Inject(method = "tick", at = @At("HEAD"))
private void onTick(CallbackInfo ci) {
    ItemEntity entity = (ItemEntity) (Object) this;
    Level level = entity.level();

    if (level.isClientSide()) return;

    BlockPos pos = entity.blockPosition();
    ItemStack stack = entity.getItem();
    Item item = stack.getItem();

    // If in saw table and has a recycle recipe
    if (level.getBlockState(pos).is(Blocks.STONECUTTER) && SAW_TABLE_RECIPES.containsKey(item)) {
            RecycleResult recipe = SAW_TABLE_RECIPES.get(item);

            int count = stack.getCount() * recipe.count();
            ItemStack result = new ItemStack(recipe.result(), count);

            // Spawn new item(s) above saw table
            ItemEntity newEntity = new ItemEntity(level, entity.getX(), entity.getY() + 0.6, entity.getZ(), result);
            level.addFreshEntity(newEntity);

            // Particles & sounds & TNT
            if (level instanceof ServerLevel serverLevel) {

                if (getItem().is(Items.TNT_MINECART)) {
                    serverLevel.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 1.0F, true, Level.ExplosionInteraction.TNT);
                }
                if (getItem().is(Items.TNT)) {
                    serverLevel.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 3.0F, true, Level.ExplosionInteraction.TNT);
                }

                serverLevel.levelEvent(2001, pos, net.minecraft.world.level.block.Block.getId(level.getBlockState(pos)));
                level.playSound(entity, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1, 1);
            }

        entity.discard();
    }
}

    static {
        SAW_TABLE_RECIPES.put(Items.ROTTEN_FLESH, new RecycleResult(ModItems.SHREDDED_FLESH, 2));

        // Log items
        SAW_TABLE_RECIPES.put(Items.OAK_LOG, new RecycleResult(Blocks.OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.OAK_WOOD, new RecycleResult(Blocks.OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_OAK_LOG, new RecycleResult(Blocks.OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_OAK_WOOD, new RecycleResult(Blocks.OAK_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.SPRUCE_LOG, new RecycleResult(Blocks.SPRUCE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.SPRUCE_WOOD, new RecycleResult(Blocks.SPRUCE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_SPRUCE_LOG, new RecycleResult(Blocks.SPRUCE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_SPRUCE_WOOD, new RecycleResult(Blocks.SPRUCE_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.BIRCH_LOG, new RecycleResult(Blocks.BIRCH_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.BIRCH_WOOD, new RecycleResult(Blocks.BIRCH_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_BIRCH_LOG, new RecycleResult(Blocks.BIRCH_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_BIRCH_WOOD, new RecycleResult(Blocks.BIRCH_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.JUNGLE_LOG, new RecycleResult(Blocks.JUNGLE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.JUNGLE_WOOD, new RecycleResult(Blocks.JUNGLE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_JUNGLE_LOG, new RecycleResult(Blocks.JUNGLE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_JUNGLE_WOOD, new RecycleResult(Blocks.JUNGLE_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.ACACIA_LOG, new RecycleResult(Blocks.ACACIA_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.ACACIA_WOOD, new RecycleResult(Blocks.ACACIA_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_ACACIA_LOG, new RecycleResult(Blocks.ACACIA_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_ACACIA_WOOD, new RecycleResult(Blocks.ACACIA_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.DARK_OAK_LOG, new RecycleResult(Blocks.DARK_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.DARK_OAK_WOOD, new RecycleResult(Blocks.DARK_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_DARK_OAK_LOG, new RecycleResult(Blocks.DARK_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_DARK_OAK_WOOD, new RecycleResult(Blocks.DARK_OAK_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.MANGROVE_LOG, new RecycleResult(Blocks.MANGROVE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.MANGROVE_WOOD, new RecycleResult(Blocks.MANGROVE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_MANGROVE_LOG, new RecycleResult(Blocks.MANGROVE_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_MANGROVE_WOOD, new RecycleResult(Blocks.MANGROVE_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.CHERRY_LOG, new RecycleResult(Blocks.CHERRY_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.CHERRY_WOOD, new RecycleResult(Blocks.CHERRY_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_CHERRY_LOG, new RecycleResult(Blocks.CHERRY_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_CHERRY_WOOD, new RecycleResult(Blocks.CHERRY_PLANKS.asItem(), 4));

        SAW_TABLE_RECIPES.put(Items.PALE_OAK_LOG, new RecycleResult(Blocks.PALE_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.PALE_OAK_WOOD, new RecycleResult(Blocks.PALE_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_PALE_OAK_LOG, new RecycleResult(Blocks.PALE_OAK_PLANKS.asItem(), 4));
        SAW_TABLE_RECIPES.put(Items.STRIPPED_PALE_OAK_WOOD, new RecycleResult(Blocks.PALE_OAK_PLANKS.asItem(), 4));

        // Stone Bricks

        SAW_TABLE_RECIPES.put(Items.STONE, new RecycleResult(Blocks.STONE_BRICKS.asItem(), 2));
        SAW_TABLE_RECIPES.put(Items.COBBLESTONE, new RecycleResult(Blocks.STONE_BRICKS.asItem(), 1));

        SAW_TABLE_RECIPES.put(Items.DEEPSLATE, new RecycleResult(Blocks.DEEPSLATE_BRICKS.asItem(), 2));
        SAW_TABLE_RECIPES.put(Items.COBBLED_DEEPSLATE, new RecycleResult(Blocks.DEEPSLATE_BRICKS.asItem(), 1));

        // Ores

        SAW_TABLE_RECIPES.put(Items.COAL_ORE, new RecycleResult(Items.COAL, 4));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_COAL_ORE, new RecycleResult(Items.COAL, 6));

        SAW_TABLE_RECIPES.put(Items.IRON_ORE, new RecycleResult(Items.RAW_IRON, 2));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_IRON_ORE, new RecycleResult(Items.RAW_IRON, 4));

        SAW_TABLE_RECIPES.put(ModBlocks.HARDENED_IRON_ORE.asItem(), new RecycleResult(Items.RAW_IRON, 3));
        SAW_TABLE_RECIPES.put(ModBlocks.HARDENED_DEEPSLATE_IRON_ORE.asItem(), new RecycleResult(Items.RAW_IRON, 6));

        SAW_TABLE_RECIPES.put(Items.COPPER_ORE, new RecycleResult(Items.RAW_COPPER, 4));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_COPPER_ORE, new RecycleResult(Items.RAW_COPPER, 8));

        SAW_TABLE_RECIPES.put(Items.GOLD_ORE, new RecycleResult(Items.RAW_GOLD, 3));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_GOLD_ORE, new RecycleResult(Items.RAW_GOLD, 6));

        SAW_TABLE_RECIPES.put(Items.EMERALD_ORE, new RecycleResult(Items.EMERALD, 6));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_EMERALD_ORE, new RecycleResult(Items.EMERALD, 12));

        SAW_TABLE_RECIPES.put(Items.LAPIS_ORE, new RecycleResult(Items.LAPIS_LAZULI, 9));
        SAW_TABLE_RECIPES.put(Items.DEEPSLATE_LAPIS_ORE, new RecycleResult(Items.LAPIS_LAZULI, 14));
    }
}
