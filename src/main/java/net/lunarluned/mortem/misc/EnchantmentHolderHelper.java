package net.lunarluned.mortem.misc;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class EnchantmentHolderHelper {

    public static <T> Holder.Reference<T> resolveHolder(Level level, ResourceKey<? extends Registry<T>> registryKey, ResourceKey<T> elementKey) {
        Registry<T> registry = level.registryAccess().lookupOrThrow(registryKey);
        return registry.get(elementKey)
                .orElseThrow(() -> new IllegalStateException("Missing holder for " + elementKey));
    }
}
