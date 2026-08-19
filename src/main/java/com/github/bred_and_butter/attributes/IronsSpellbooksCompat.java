package com.github.bred_and_butter.attributes;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

public class IronsSpellbooksCompat {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void manaLeech(LivingDamageEvent event) {
        Player attacker;
        Entity entity = event.getSource().getEntity();
        LOGGER.info("entered mana leech event");
        if (entity instanceof Player) {
            attacker = (Player) entity;
        } else return;

        LOGGER.info("is player");
        double manaLeech = attacker.getAttributeValue(AttributeRegister.MANA_LEECH.get());
        if (manaLeech <= 0) return;
        float percentManaLeech = (float)(manaLeech/100);

        float damage = event.getAmount();

        if (ModList.get().isLoaded("irons_spellbooks")) {
            LOGGER.info("spellbooks mod loaded");
            MagicData pmg = MagicData.getPlayerMagicData(attacker);
            pmg.setMana(pmg.getMana() + (damage * percentManaLeech));
            Messages.sendToPlayer(new SyncManaPacket(pmg), (ServerPlayer)attacker);
        }
    }
}
