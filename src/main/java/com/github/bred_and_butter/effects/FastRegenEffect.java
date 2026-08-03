package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.attributes.AttributeRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FastRegenEffect extends MobEffect {
    public static final String UUID_STRING_DELAY = "66b4bf7e-7424-4db1-9ea3-f1a67ec27b77";
    public static final String UUID_STRING_RATE = "3c588592-0875-439d-bdb4-3390f492b7e0";

    public FastRegenEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(AttributeRegister.ENERGY_SHIELD_RECHARGE_DELAY.get(), UUID_STRING_DELAY, -1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(AttributeRegister.ENERGY_SHIELD_RECHARGE_RATE.get(), UUID_STRING_RATE, 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
