package com.github.bred_and_butter.mixin;

import com.github.bred_and_butter.util.InterfaceAmplifier;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MobEffectInstance.class)
public class MobEffectsInstanceMixin implements InterfaceAmplifier {
    @Shadow
    private int amplifier;

    @Unique
    public void simple_attributes$setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
}
