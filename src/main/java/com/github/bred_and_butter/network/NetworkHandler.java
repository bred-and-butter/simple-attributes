package com.github.bred_and_butter.network;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SimpleAttributes.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(
                packetId++,
                SyncEnergyShieldPacket.class,
                SyncEnergyShieldPacket::encode,
                SyncEnergyShieldPacket::decode,
                SyncEnergyShieldPacket::handle
        );
    }
}
