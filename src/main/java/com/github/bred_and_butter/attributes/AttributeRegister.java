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
}
