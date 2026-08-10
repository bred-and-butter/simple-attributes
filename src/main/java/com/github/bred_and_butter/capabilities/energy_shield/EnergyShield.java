package com.github.bred_and_butter.capabilities.energy_shield;

public class EnergyShield {
    private float shield = 0;
    private int ticksSinceLastDamage;
    private boolean isBroken;

    public float getShield() { return shield; }

    public void setShield(float shield) { this.shield = Math.max(0, shield); }

    public boolean isBroken () {
        return this.isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
        this.shield = 0;
    }

    public int getTicksSinceLastDamage() { return ticksSinceLastDamage; }

    public void resetTicksSinceLastDamage() { this.ticksSinceLastDamage = 0; }

    public void incrementTicksSinceLastDamage() { this.ticksSinceLastDamage++; }
}