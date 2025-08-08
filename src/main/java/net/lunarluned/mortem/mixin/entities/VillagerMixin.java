package net.lunarluned.mortem.mixin.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "golemSpawnConditionsMet", at = @At("HEAD"), cancellable = true)
    private void mortem_golemSpawnConditionsMet(long l, CallbackInfoReturnable<Boolean> cir) {
        // okay so this just adds more time for iron golems to spawn, i wanted to 'buff' iron golem spawns
        // but its kinda impossible because they just spawn one if they dont detect it. i suffer because
        // i never cheated the system.

        Optional<Long> optional = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
        cir.setReturnValue(optional.filter((long_) -> l - long_ < 36000L).isPresent());
    }
}
