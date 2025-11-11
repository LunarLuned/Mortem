package net.lunarluned.mortem.sounds;

import net.lunarluned.mortem.Mortem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MortemSoundEvents {

    // Nether Sounds

    public static final SoundEvent FUNGAL_TERRORS = registerSoundEvent("effect.fungal_infection.terrors");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation resourceLocation = Mortem.id(name);
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(resourceLocation);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, soundEvent);
    }

    public static void registerSounds() {}
}
