package com.github.bred_and_butter.capabilities.energy_shield;

public interface IEnergyShield {
    float getShield();
    void setShield(float shield);
    long getLastDamageTick();
    void setLastDamageTick(long tick);
}
