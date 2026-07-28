package com.github.bred_and_butter.capabilities.energy_shield;

public class EnergyShield implements IEnergyShield {
    private float shield;
    private long lastDamageTick;

    @Override
    public float getShield() { return shield; }

    @Override
    public void setShield(float shield) { this.shield = Math.max(0, shield); }

    @Override
    public long getLastDamageTick() { return lastDamageTick; }

    @Override
    public void setLastDamageTick(long tick) { this.lastDamageTick = tick; }
}