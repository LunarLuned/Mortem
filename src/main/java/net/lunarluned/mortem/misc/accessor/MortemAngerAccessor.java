package net.lunarluned.mortem.misc.accessor;

import net.minecraft.world.entity.LivingEntity;

public interface MortemAngerAccessor {

    LivingEntity mortem_getAngryAt();

    void mortem_setAngryAt(LivingEntity target);
}