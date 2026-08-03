package com.github.bred_and_butter.effects;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SimpleAttributes.MODID);

    public static final RegistryObject<MobEffect> BROKEN_SHIELD = MOB_EFFECTS.register(
            "broken_shield", () -> new BrokenShieldEffect(MobEffectCategory.HARMFUL, 0x929c9b));

    public static final RegistryObject<MobEffect> BONUS_SHIELD = MOB_EFFECTS.register(
            "bonus_shield", () -> new BonusShieldEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF));

    public static final RegistryObject<MobEffect> FAST_REGEN = MOB_EFFECTS.register(
            "fast_regen", () -> new FastRegenEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF));
}
