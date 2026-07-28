package com.github.bred_and_butter.network;

import com.github.bred_and_butter.client.ClientEnergyShield;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncEnergyShieldPacket {
    private final float shield;

    public SyncEnergyShieldPacket(float shield) { this.shield = shield; }

    public static void encode(SyncEnergyShieldPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.shield);
    }

    public static SyncEnergyShieldPacket decode(FriendlyByteBuf buf) {
        return new SyncEnergyShieldPacket(buf.readFloat());
    }

    public static void handle(SyncEnergyShieldPacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            // This runs on the main client thread
            ClientEnergyShield.set(msg.shield);
        });
        context.setPacketHandled(true);
    }
}
