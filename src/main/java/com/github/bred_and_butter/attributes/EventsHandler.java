package com.github.bred_and_butter.attributes;

import com.github.bred_and_butter.SimpleAttributes;
import com.github.bred_and_butter.capabilities.energy_shield.EnergyShieldCapability;
import com.github.bred_and_butter.network.NetworkHandler;
import com.github.bred_and_butter.network.SyncEnergyShieldPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class EventsHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // Tamed Damage – bonus damage dealt by tamed mobs
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof TamableAnimal attacker
                && attacker.isTame()
                && attacker.getOwner() instanceof Player owner) {

            double bonus = owner.getAttributeValue(AttributeRegister.TAMED_DAMAGE.get());
            if (bonus > 0) {
                event.setAmount((float) (event.getAmount() * (1 + (bonus/100))));
            }
        }

        // Tamed Resistance – damage reduction for tamed mobs
        if (event.getEntity() instanceof TamableAnimal victim
                && victim.isTame()
                && victim.getOwner() instanceof Player owner) {

            double reduction = owner.getAttributeValue(AttributeRegister.TAMED_RESISTANCE.get());
            if (reduction > 0) {
                event.setAmount(Math.max(1, (float) (event.getAmount() * (1 - (reduction/100)))));
            }
        }

        // Energy Shield Protection
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            player.getCapability(EnergyShieldCapability.INSTANCE).ifPresent(shield -> {
                float currentShield = shield.getShield();
                if (currentShield > 0) {
                    float absorbed = Math.min(event.getAmount(), currentShield);
                    shield.setShield(currentShield - absorbed);

                    if (!player.level().isClientSide) {
                        NetworkHandler.CHANNEL.send(
                                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                                new SyncEnergyShieldPacket(shield.getShield())
                        );
                    }

                    event.setAmount(event.getAmount() - absorbed);

                    shield.setLastDamageTick(player.level().getGameTime());
                } else {
                    if (event.getAmount() > 0) {
                        shield.setLastDamageTick(player.level().getGameTime());
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        Player player = event.player;
        player.getCapability(EnergyShieldCapability.INSTANCE).ifPresent(shield -> {
            double maxShield = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD.get());
            float current = shield.getShield();

            // Clamp to new max if it was reduced externally
            if (current > maxShield) {
                shield.setShield((float) maxShield);
                current = (float) maxShield;
            }

            if (current < maxShield && player.isAlive()) {
                long gameTime = player.level().getGameTime();
                long lastDamage = shield.getLastDamageTick();
                double delaySeconds = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD_RECHARGE_DELAY.get());
                long delayTicks = (long) (delaySeconds * 20);

                if (gameTime - lastDamage >= delayTicks) {
                    double rate = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD_RECHARGE_RATE.get());
                    double perTick = rate / 10.0;   // amount per half‑second → per tick
                    float newShield = (float) Math.min(maxShield, current + perTick);
                    shield.setShield(newShield);

                    NetworkHandler.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                            new SyncEnergyShieldPacket(newShield)
                    );
                }
            }
        });
    }
}
