package com.github.bred_and_butter.capabilities.energy_shield;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class EnergyShieldCapability {
    public static final Capability<EnergyShield> INSTANCE =
            CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(EnergyShield.class);
    }
}
