package com.github.bred_and_butter.client;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bred_and_butter.client.GuiOverlayRegister.SHIELD_GUI;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "energy_shield", SHIELD_GUI);
    }
}
