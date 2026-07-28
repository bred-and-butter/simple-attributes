package com.github.bred_and_butter.client;

import com.github.bred_and_butter.SimpleAttributes;
import com.mojang.blaze3d.systems.RenderSystem;
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
        //PoseStack poseStack = guiGraphics.pose();
        ResourceLocation guiIconsLocation = new ResourceLocation("simpleattributes", "textures/overlay/icons.png");
        //ResourceLocation guiIconsLocation = new ResourceLocation("textures/gui/icons.png");
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;

        float currentShield = ClientEnergyShield.get();
        if (currentShield <= 0.0F) return;

        // Convert to number of full half‑hearts to draw (rounded up for the last one)
        int shieldHalfHearts = Mth.ceil(currentShield);
        // How many half‑hearts of regular health the player has (used for layout)
        //int healthHalfHearts = Mth.ceil(player.getHealth());
        int absorptionHalfHearts = Mth.ceil(player.getAbsorptionAmount());

        // Vanilla positions for the health bar – same as Gui.renderHealth()
        int left = screenWidth / 2 - 91;
        int shieldTop = getShieldTop(screenHeight, absorptionHalfHearts);

        // Max half‑hearts per row (10 hearts = 20 half‑hearts)
        final int perRow = 20;

        // Set up rendering tint – light blue colour for energy shield
        RenderSystem.setShaderTexture(0, guiIconsLocation);
        RenderSystem.enableBlend();
        //RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F); // blueish tint

        for (int i = 0; i < shieldHalfHearts; i++) {
            int row = i / perRow;          // 0 = first row, 1 = second, etc.
            int col = i % perRow;          // 0–19
            int x = left + (col % 10) * 8; // 10 hearts per visual row, 8px apart
            int y = shieldTop - row * 10;  // each additional row moves upward


            int u = getU(i, shieldHalfHearts, currentShield);

            guiGraphics.blit(guiIconsLocation, x, y, u, 0, 9, 9, 256, 256);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

    private static int getU(int i, int shieldHalfHearts, float currentShield) {
        // Determine whether this slot is a full heart or half heart
        boolean half = (i == shieldHalfHearts - 1) && (currentShield % 1.0F != 0.0F);
        //int vOffset = 0;               // vanilla heart texture vertical offset (full or half)
        //int uOffset = 16;              // start of the first heart icon (0 is the container)

        // Vanilla heart icons:
        // full heart: (u=52, v=0) for the fill, but the container is separate.
        // Actually Gui#renderHearts uses a complex blending approach with multiple layers.
        // Simpler: draw a full heart for the entire slot, using the “full” icon (u=88, v=0) or similar.
        // We'll use the absorption-like heart appearance: we'll just draw the “full” icon tinted.
        // The icon at (16, 0) is the container, (52, 0) is the filled red heart, (88, 0) is the absorption golden heart.
        // We'll use the golden heart icon and tint it blue.
        int u = 88; // absorption heart full base
        if (half) {
            u = 97; // absorption heart half (right half)
        }
        return u;
    }

    private static int getShieldTop(int screenHeight, int absorptionHalfHearts) {
        int top = screenHeight - 39; // survival inventory top

        // Row offsets: health row is at top, absorption row above it (y - 10), shield row above that
        // If absorption exists, shield goes one row higher; otherwise just above health
        int shieldTop = top;
        if (absorptionHalfHearts > 0) {
            shieldTop -= 20; // absorption occupies the row above health, we go above it
        } else {
            shieldTop -= 10; // no absorption, shield directly above health
        }
        return shieldTop;
    }
}
