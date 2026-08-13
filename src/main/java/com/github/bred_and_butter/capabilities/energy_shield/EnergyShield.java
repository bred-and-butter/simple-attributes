package com.github.bred_and_butter.capabilities.energy_shield;

public class EnergyShield {
    private float shield = 0;
    private int ticksSinceLastDamage;

    public float getShield() { return shield; }

    public void setShield(float shield) { this.shield = Math.max(0, shield); }

    public int getTicksSinceLastDamage() { return ticksSinceLastDamage; }

    public void resetTicksSinceLastDamage() { this.ticksSinceLastDamage = 0; }

    public void incrementTicksSinceLastDamage() { this.ticksSinceLastDamage++; }
}