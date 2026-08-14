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
            "broken_shield", () -> new BrokenShieldEffect(MobEffectCategory.HARMFUL, 0x929c9b)
    );

    public static final RegistryObject<MobEffect> BONUS_SHIELD = MOB_EFFECTS.register(
            "shield_boost", () -> new ShieldBoostEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF)
    );

    public static final RegistryObject<MobEffect> FAST_REGEN = MOB_EFFECTS.register(
            "fast_regen", () -> new FastRegenEffect(MobEffectCategory.BENEFICIAL, 0x1E24CC)
    );

    public static final RegistryObject<MobEffect> INSTANT_SHIELD = MOB_EFFECTS.register(
            "instant_shield", () -> new InstantShieldEffect(MobEffectCategory.BENEFICIAL, 0x00A3CC)
    );

    public static final RegistryObject<MobEffect> REGENERATING = MOB_EFFECTS.register(
            "regenerating", () -> new RegeneratingEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF)
    );

    public static final RegistryObject<MobEffect> SPENT_SHIELD = MOB_EFFECTS.register(
            "spent_shield", () -> new SpentShieldEffect(MobEffectCategory.HARMFUL, 0x808080)
    );

    public static final RegistryObject<MobEffect> REDUCED_SHIELD = MOB_EFFECTS.register(
            "reduced_shield", () -> new ReducedShieldEffect(MobEffectCategory.HARMFUL, 0x929c9b)
    );
}
