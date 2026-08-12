package com.github.bred_and_butter.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiOverlayRegister {
    private static final ResourceLocation SHIELD_HEARTS_LOCATION = new ResourceLocation("simpleattributes", "textures/overlay/icons.png");

    public static final IGuiOverlay SHIELD_GUI =
            (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
                if (gui.getMinecraft().player == null || !gui.shouldDrawSurvivalElements() || Minecraft.getInstance().options.hideGui) return;

                gui.setupOverlayRenderState(true, false);

                RenderSystem.depthMask(false);

                Minecraft.getInstance().getProfiler().push("shield_gui");
                renderEnergyShield(guiGraphics, screenWidth, screenHeight);
                Minecraft.getInstance().getProfiler().pop();

                RenderSystem.depthMask(true);

                gui.setupOverlayRenderState(false, false);
            };

    private static void renderEnergyShield(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
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
    }
}
