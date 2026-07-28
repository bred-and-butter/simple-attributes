package com.github.bred_and_butter.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientEnergyShield {
    private static float shield = 0f;

    public static float get() {
        return shield;
    }

    public static void set(float value) {
        shield = value;
    }
}
