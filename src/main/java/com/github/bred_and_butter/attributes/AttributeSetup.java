package com.github.bred_and_butter.attributes;

import com.github.bred_and_butter.SimpleAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeSetup {
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        // Add both custom attributes to the player entity
        event.add(EntityType.PLAYER, AttributeRegister.TAMED_DAMAGE.get());
        event.add(EntityType.PLAYER, AttributeRegister.TAMED_RESISTANCE.get());
    }

}
