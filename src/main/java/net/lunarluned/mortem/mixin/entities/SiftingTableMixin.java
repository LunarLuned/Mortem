package net.lunarluned.mortem.mixin.entities;

import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ItemEntity.class)
public abstract class SiftingTableMixin {

    @Shadow
    public abstract ItemStack getItem();



    @Unique
    private static final Map<Item, ResourceKey<LootTable>> SIFTING_RECIPES = new HashMap<>();
    @Inject(method = "tick", at = @At("HEAD"))
    private void mortem_siftingTableOnTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity)(Object)this;
        Level level = entity.level();

        if (level.isClientSide()) return;

        BlockPos pos = entity.blockPosition();

        if (!level.getBlockState(pos).is(ModBlocks.SIFTING_TABLE)) return;

        // Process once every 10 ticks
        if (entity.tickCount % 10 != 0) return;

        ItemStack stack = entity.getItem();
        Item item = stack.getItem();

        ResourceKey<LootTable> lootTableId = SIFTING_RECIPES.get(item);
        if (lootTableId == null) return;

        ServerLevel serverLevel = (ServerLevel) level;

        LootTable lootTable = serverLevel.getServer()
                .reloadableRegistries()
                .getLootTable(lootTableId);

        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .create(LootContextParamSets.CHEST);

        List<ItemStack> loot = lootTable.getRandomItems(params);

        if (level.getBlockState(pos.below()).is(Blocks.AIR)) {
            for (ItemStack out : loot) {
                level.addFreshEntity(new ItemEntity(level, entity.getX(), entity.getY() - 1.6, entity.getZ(), out));
            }
        }
        else for (ItemStack out : loot) {
            level.addFreshEntity(new ItemEntity(level, entity.getX(), entity.getY() + 0.6, entity.getZ(), out));
        }

        // Use only ONE item from the stack . . . no stack using at once oh my god
        stack.shrink(1);

        if (stack.isEmpty()) {
            entity.discard();
        } else {
            entity.setItem(stack);
        }

        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY() + 0.3, entity.getZ(), 2, 0.2, 0.2, 0.2, 0.01);

        level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    static {
        SIFTING_RECIPES.put(Items.DIRT, ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("mortem", "sifting/dirt")));
        SIFTING_RECIPES.put(Items.GRAVEL, ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("mortem", "sifting/gravel")));
    }
}