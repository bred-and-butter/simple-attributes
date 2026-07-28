package com.github.bred_and_butter.capabilities.energy_shield;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class CapabilityAttacher {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation(SimpleAttributes.MODID, "energy_shield"),
                    new EnergyShieldProvider()
            );
        }
    }
}
