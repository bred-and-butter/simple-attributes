package com.github.bred_and_butter.client;

import com.github.bred_and_butter.SimpleAttributes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleAttributes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GuiOverlayRegister {
    private static final ResourceLocation SHIELD_HEARTS_LOCATION = new ResourceLocation("simpleattributes", "textures/overlay/icons.png");

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(
                VanillaGuiOverlay.PLAYER_HEALTH.id(),
                "energy_shield",
                (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
                    if (gui.getMinecraft().player == null) return;
                    renderEnergyShield(gui, guiGraphics, screenWidth, screenHeight);
                }
        );
    }

    private static void renderEnergyShield(ForgeGui gui, GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        Minecraft mc = gui.getMinecraft();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;

        float currentShield = ClientEnergyShield.get();
        if (currentShield <= 0.0F) return;

        // Convert to number of full half‑hearts to draw (rounded up for the last one)
        int shieldHalfHearts = Mth.ceil(currentShield/2);
        // How many half‑hearts of regular health the player has (used for layout)
        //int healthHalfHearts = Mth.ceil(player.getHealth());
        //int absorptionHalfHearts = Mth.ceil(player.getAbsorptionAmount());

        // Vanilla positions for the health bar – same as Gui.renderHealth()
        int left = screenWidth / 2 - 91;
        int top = screenHeight - 39; // survival inventory top

        final int perRow = 10;

        // Set up rendering tint – light blue colour for energy shield
        RenderSystem.setShaderTexture(0, SHIELD_HEARTS_LOCATION);
        RenderSystem.enableBlend();

        for (int i = 0; i < shieldHalfHearts; i++) {
            int row = i / perRow;          // 0 = first row, 1 = second, etc.
            int col = i % perRow;          // 0–19
            int x = left + (col % 10) * 8; // 10 hearts per visual row, 8px apart
            int y = top - (row * 10);  // each additional row moves upward

            boolean half = (i == shieldHalfHearts - 1) && (currentShield % 2.0F != 0.0F);

            // The icon at (16, 0) is the container, (52, 0) is the filled red heart, (88, 0) is the blue heart.
            int u  = half ? 97 : 88;

            guiGraphics.blit(SHIELD_HEARTS_LOCATION, x, y, u, 0, 9, 9, 256, 256);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
