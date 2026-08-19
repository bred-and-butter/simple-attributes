package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.attributes.AttributeRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ReducedShieldEffect extends MobEffect {
    public static final String UUID_STRING = "ca962388-21a7-4609-a7a5-775e117b1a8a";

    public ReducedShieldEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(AttributeRegister.ENERGY_SHIELD_MAX.get(), UUID_STRING, -2.0f, AttributeModifier.Operation.ADDITION);
    }
}
