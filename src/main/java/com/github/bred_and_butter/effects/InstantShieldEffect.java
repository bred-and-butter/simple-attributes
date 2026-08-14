package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.attributes.AttributeRegister;
import com.github.bred_and_butter.capabilities.energy_shield.EnergyShieldProvider;
import com.github.bred_and_butter.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InstantShieldEffect extends InstantenousMobEffect {
    public InstantShieldEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity target, int amplifier, double health) {
        double maxShield = target.getAttributeValue(AttributeRegister.ENERGY_SHIELD_MAX.get());

        if (maxShield > 0) {
            target.getCapability(EnergyShieldProvider.INSTANCE).ifPresent(shield -> {
                float currentShield = shield.getShield();
                float recoveredShield = 4 * (amplifier + 1);
                float finalShield = currentShield + recoveredShield;

                shield.setShield(Math.min(finalShield, (float) maxShield));

                if (target instanceof ServerPlayer serverPlayer) NetworkHandler.syncShieldToClient(serverPlayer, shield);
            });
        }
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int pAmplifier) {
        applyInstantenousEffect(null, null, livingEntity, pAmplifier, livingEntity.getHealth());
    }
}
