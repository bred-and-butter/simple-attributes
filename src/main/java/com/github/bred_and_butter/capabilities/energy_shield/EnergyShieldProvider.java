package com.github.bred_and_butter.capabilities.energy_shield;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EnergyShieldProvider implements ICapabilitySerializable<CompoundTag>, INBTSerializable<CompoundTag> {
    public static final Capability<EnergyShield> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){});

    private final LazyOptional<EnergyShield> optional = LazyOptional.of(this::createEnergyShield);

    private EnergyShield energyShield = null;

    private EnergyShield createEnergyShield() {
        if (this.energyShield == null) {
            this.energyShield = new EnergyShield();
        }
        return this.energyShield;
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == INSTANCE ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putFloat("currentShield", createEnergyShield().getShield());

        return nbt;
    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createEnergyShield().setShield(nbt.getFloat("currentShield"));
    }
}
