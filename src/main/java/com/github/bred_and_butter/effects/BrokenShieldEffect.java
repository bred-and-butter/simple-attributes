package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.attributes.AttributeRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class BrokenShieldEffect extends MobEffect {
    public static final String UUID_STRING = "40aaf0a9-67a2-4cf1-bcbe-edcb91226945";

    public BrokenShieldEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(AttributeRegister.ENERGY_SHIELD_MAX.get(), UUID_STRING, -1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
