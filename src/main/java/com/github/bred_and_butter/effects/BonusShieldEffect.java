package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.attributes.AttributeRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class BonusShieldEffect extends MobEffect {
    public static final String UUID_STRING = "58fc4292-30f0-4d6e-a8b3-9ebae055d208";

    public BonusShieldEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(AttributeRegister.ENERGY_SHIELD_MAX.get(), UUID_STRING, 4f, AttributeModifier.Operation.ADDITION);
    }
}
