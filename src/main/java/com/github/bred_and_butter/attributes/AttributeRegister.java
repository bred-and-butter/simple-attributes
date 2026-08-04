package com.github.bred_and_butter.attributes;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributeRegister {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SimpleAttributes.MODID);

    // Tamed Damage: default 0.0, min -100.0, max 1024.0
    public static final RegistryObject<Attribute> TAMED_DAMAGE = ATTRIBUTES.register(
            "tamed_damage",
            () -> new RangedAttribute("attribute.simpleattributes.tamed_damage", 0.0D, -100.0D, 1024.0D).setSyncable(true)
    );

    // Tamed Resistance: default 0.0, min -1024.0, max 100.0 (represents 100% damage reduction)
    public static final RegistryObject<Attribute> TAMED_RESISTANCE = ATTRIBUTES.register(
            "tamed_resistance",
            () -> new RangedAttribute("attribute.simpleattributes.tamed_resistance", 0.0D, -1024.0D, 100.0D).setSyncable(true)
    );

    public static final RegistryObject<Attribute> ENERGY_SHIELD_MAX =
            ATTRIBUTES.register("energy_shield_max",
                    () -> new RangedAttribute("attribute.simpleattributes.energy_shield_max", 0.0, 0.0, 1024.0)
                            .setSyncable(true));

    public static final RegistryObject<Attribute> ENERGY_SHIELD_RECHARGE_DELAY =
            ATTRIBUTES.register("energy_shield_recharge_delay",
                    () -> new RangedAttribute("attribute.simpleattributes.energy_shield_recharge_delay", 5.0, 0.0, 3600.0)
                            .setSyncable(true));

    public static final RegistryObject<Attribute> ENERGY_SHIELD_RECHARGE_RATE =
            ATTRIBUTES.register("energy_shield_recharge_rate",
                    () -> new RangedAttribute("attribute.simpleattributes.energy_shield_recharge_rate", 1.0, 0.0, 1024.0)
                            .setSyncable(true));

    public static final RegistryObject<Attribute> LIFE_STEAL =
            ATTRIBUTES.register("life_steal",
                    () -> new RangedAttribute("attribute.simpleattributes.life_steal", 0.0, 0.0, 1024.0)
                            .setSyncable(true));
}
