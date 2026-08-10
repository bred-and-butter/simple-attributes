package com.github.bred_and_butter;

import com.github.bred_and_butter.attributes.AttributeRegister;
import com.github.bred_and_butter.capabilities.energy_shield.EnergyShield;
import com.github.bred_and_butter.capabilities.energy_shield.EnergyShieldProvider;
import com.github.bred_and_butter.network.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class EventsHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (!player.level().isClientSide) {
            player.getCapability(EnergyShieldProvider.INSTANCE).ifPresent(shield -> {
                NetworkHandler.syncShieldToClient(player, shield);
            });
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(EnergyShieldProvider.INSTANCE).ifPresent(
                    oldStore -> event.getOriginal().getCapability(EnergyShieldProvider.INSTANCE)
            );
        }
    }

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
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        // Energy Shield Protection
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            player.getCapability(EnergyShieldProvider.INSTANCE).ifPresent(shield -> {
                shield.resetTicksSinceLastDamage();

                LOGGER.info(String.valueOf(shield.getShield()));

               float currentShield = shield.getShield();
                if (currentShield > 0) {
                    float absorbed = Math.min(event.getAmount(), currentShield);
                    shield.setShield(currentShield - absorbed);

                    NetworkHandler.syncShieldToClient(player, shield);

                    event.setAmount(event.getAmount() - absorbed);
                }
            });
        }
    }

    @SubscribeEvent
    public static void lifeSteal(LivingDamageEvent event) {
        Player player;
        Entity entity = event.getSource().getEntity();
        if (entity instanceof Player) {
            player = (Player) entity;
        } else return;

        double lifeSteal = player.getAttributeValue(AttributeRegister.LIFE_STEAL.get());
        player.heal((float) (event.getAmount() * lifeSteal/100));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        // Energy Shield Regen
        Player player = event.player;
        player.getCapability(EnergyShieldProvider.INSTANCE).ifPresent(shield -> {
            double maxShield = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD_MAX.get());
            float current = shield.getShield();

            // Clamp to new max if it was reduced externally
            if (current > maxShield) {
                shield.setShield((float) maxShield);
                current = (float) maxShield;
                NetworkHandler.syncShieldToClient(player, shield);
            }

            if (current < maxShield && player.isAlive()) {
                int playerTime = player.tickCount;
                int lastDamage = shield.getTicksSinceLastDamage();
                double shieldDelay = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD_RECHARGE_DELAY.get());
                int delayTicks = (int) (shieldDelay * 20);

                if (lastDamage >= delayTicks) {
                    double rate = player.getAttributeValue(AttributeRegister.ENERGY_SHIELD_RECHARGE_RATE.get());
                    if (playerTime % 10 == 0) {
                        float newShield = (float) Math.min(maxShield, current + rate);
                        shield.setShield(newShield);

                        NetworkHandler.syncShieldToClient(player, shield);
                    }
                } else {
                    shield.incrementTicksSinceLastDamage();
                }
            }
        });
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(EnergyShield.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation(SimpleAttributes.MODID, "energy_shield"),
                    new EnergyShieldProvider()
            );
        }
    }
}
