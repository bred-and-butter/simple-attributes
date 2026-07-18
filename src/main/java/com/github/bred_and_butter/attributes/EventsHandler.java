package com.github.bred_and_butter.attributes;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID)
public class EventsHandler {
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
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

    @SubscribeEvent
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
    }

}
