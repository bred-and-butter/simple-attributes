package com.github.bred_and_butter.attributes;

import com.github.bred_and_butter.SimpleAttributes;
import com.mojang.logging.LogUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class EventsHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    /*@SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingAttack(LivingAttackEvent event) {
        LOGGER.info("entered on attack event");

        DamageSource source = event.getSource();
        // Check if the attacker is a tamed creature whose owner is a player
        if (source.getEntity() instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() instanceof Player owner) {
            double bonusDamage = owner.getAttributeValue(AttributeRegister.TAMED_DAMAGE.get());
            if (bonusDamage > 0) {
                // Reapply damage with the bonus
                Entity target = event.getEntity();
                float originalDamage = event.getAmount();
                event.setCanceled(true);
                target.hurt(source, (float)(originalDamage * (1 + (bonusDamage/100))));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        // Check if the hurt entity is a tamed creature owned by a player
        if (entity instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() instanceof Player owner) {
            double resistance = owner.getAttributeValue(AttributeRegister.TAMED_RESISTANCE.get());
            if (resistance > 0) {
                float originalDamage = event.getAmount();
                float newDamage = (float)(originalDamage * (1 - (resistance/100)));
                event.setAmount(newDamage);
            }
        }
    }*/


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 1. Tamed Damage – bonus damage dealt by tamed mobs
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof TamableAnimal attacker
                && attacker.isTame()
                && attacker.getOwner() instanceof Player owner) {

            double bonus = owner.getAttributeValue(AttributeRegister.TAMED_DAMAGE.get());
            if (bonus > 0) {
                event.setAmount((float) (event.getAmount() * (1 + (bonus/100))));
            }
        }

        // 2. Tamed Resistance – damage reduction for tamed mobs
        if (event.getEntity() instanceof TamableAnimal victim
                && victim.isTame()
                && victim.getOwner() instanceof Player owner) {

            double reduction = owner.getAttributeValue(AttributeRegister.TAMED_RESISTANCE.get());
            if (reduction > 0) {
                event.setAmount(Math.max(1, (float) (event.getAmount() * (1 - (reduction/100)))));
            }
        }
    }
}
