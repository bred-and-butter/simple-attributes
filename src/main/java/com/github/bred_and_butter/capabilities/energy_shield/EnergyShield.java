package com.github.bred_and_butter.capabilities.energy_shield;

public class EnergyShield {
    private float shield = 0;
    private long lastDamageTick;

    public float getShield() { return shield; }

    public void setShield(float shield) { this.shield = Math.max(0, shield); }

    public long getLastDamageTick() { return lastDamageTick; }

    public void setLastDamageTick(long tick) { this.lastDamageTick = tick; }
}