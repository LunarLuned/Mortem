package net.lunarluned.mortem.mixin.items;

import com.mojang.datafixers.util.Pair;
import net.lunarluned.mortem.enchantments.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.lunarluned.mortem.misc.EnchantmentHolderHelper.resolveHolder;
import static net.minecraft.world.item.HoeItem.changeIntoState;
import static net.minecraft.world.level.block.FarmBlock.MOISTURE;


@Mixin(HoeItem.class)
public abstract class HoeItemMixin extends Item {

    public HoeItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void harvestCropsWithHoe(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = useOnContext.getLevel();

        // holders for the enchantment keys
        ResourceKey<Enchantment> chiningenchantKey = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild("mortem", "chining"));
        Holder.Reference<Enchantment> holder = resolveHolder(level, Registries.ENCHANTMENT, chiningenchantKey);

        ResourceKey<Enchantment> harvestingenchantKey = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild("mortem", "harvesting"));
        Holder.Reference<Enchantment> harvestingholder = resolveHolder(level, Registries.ENCHANTMENT, harvestingenchantKey);

        if (!(level instanceof ServerLevel serverLevel) || level.isClientSide()) {
            return;
        }

        BlockPos pos = useOnContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof CropBlock cropBlock) || !cropBlock.isMaxAge(state)) {
            return;
        }

        Player player = useOnContext.getPlayer();
        EquipmentSlot slot = useOnContext.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

        ItemStack hoeItem = useOnContext.getItemInHand();
        int radius = getRadius(hoeItem, player, holder);
        if (radius < 0) {
            return;
        }

        boolean harvested = false;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos nearbyPos = pos.offset(dx, 0, dz);
                BlockState blockState = level.getBlockState(nearbyPos);
                Block nearbyBlock = blockState.getBlock();

                // side note: this and the bone meal mixin was written from studying someone elses MIT code and i didnt realize 'continue' was a result you could do!
                if (!(nearbyBlock instanceof CropBlock lastCrop)) {
                    continue;
                }

                // added bonus here, if you have harvesting 3 or above theres a 50% chance it'll replant the crop
                if (hoeItem.getEnchantments().getLevel(harvestingholder) >= 3) {
                    assert player != null;
                    if (player.getRandom().nextInt(10) <= 5) {
                        serverLevel.setBlockAndUpdate(nearbyPos, lastCrop.defaultBlockState());
                    } else serverLevel.setBlockAndUpdate(nearbyPos, Blocks.AIR.defaultBlockState());
                } else {
                    serverLevel.setBlockAndUpdate(nearbyPos, Blocks.AIR.defaultBlockState());
                }
                spawnChiningParticles(serverLevel, pos);

                int harvestingChance = getLevelForHarvesting(hoeItem, harvestingholder);
                assert player != null;
                // the entirety of the harvesting enchantment works here. if the player has the enchantment, the chance of getting increased drops raises.
                if (player.getRandom().nextInt(100) <= harvestingChance) {
                    for (int i = 0; i < 3; i++) {
                        Block.dropResources(blockState, serverLevel, nearbyPos);
                    }
                }
                Block.dropResources(blockState, serverLevel, nearbyPos);

                useOnContext.getItemInHand().hurtAndBreak(2, player, slot);

                harvested = true;
            }
        }
        if (harvested) {
            serverLevel.playSound(
                    null,
                    pos,
                    SoundEvents.CROP_BREAK,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Unique
    private int getRadius(ItemStack hoeItem, Player player, Holder<Enchantment> holder) {
        if (hoeItem.getEnchantments().getLevel(holder) == 0) {
            return 0; // single block

        } else if (hoeItem.getEnchantments().getLevel(holder) == 1) {
            if (player.getRandom().nextInt(3) > 2) {
                return 1; // 3x3
            } else return 0; // single block

        } else if (hoeItem.getEnchantments().getLevel(holder) == 2) {
            if (player.getRandom().nextInt(3) > 2) {
                return 2; // 5x5
            } else return 1; // 3x3

        } else if (hoeItem.getEnchantments().getLevel(holder) == 3) {
                return 2; // 5x5
        }
        return 0; // single block
    }

    @Unique
    private int getLevelForHarvesting(ItemStack hoeItem, Holder<Enchantment> harvestingholder) {
        if (hoeItem.getEnchantments().getLevel(harvestingholder) == 0) {
            return 0;
        } else if (hoeItem.getEnchantments().getLevel(harvestingholder) == 1) {
            return 15;
        } else if (hoeItem.getEnchantments().getLevel(harvestingholder) == 2) {
            return 30;
        } else if (hoeItem.getEnchantments().getLevel(harvestingholder) == 3) {
            return 45;
        } else if (hoeItem.getEnchantments().getLevel(harvestingholder) == 4) {
            return 60;
        } else if (hoeItem.getEnchantments().getLevel(harvestingholder) == 5) {
            return 75;
        }
        return 0;
    }

    @Unique
    private void spawnChiningParticles(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            double offsetX = level.random.nextDouble();
            double offsetY = level.random.nextDouble() * 0.5 + 0.5;
            double offsetZ = level.random.nextDouble();
            double deltaY = level.random.nextDouble() * 0.1;
            double speed = level.random.nextDouble() * 0.05;

            Vec3 particlePos = new Vec3(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            level.sendParticles(ParticleTypes.SWEEP_ATTACK, particlePos.x, particlePos.y, particlePos.z, 1, 0, deltaY, 0, speed);
        }
    }

    @Shadow
    @Final
    protected static Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES;


    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addTillingActions(CallbackInfo ci) {
        TILLABLES.put(Blocks.MYCELIUM, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState().setValue(MOISTURE, 6))));
        TILLABLES.put(Blocks.PODZOL, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState().setValue(MOISTURE, 7))));
    }
}