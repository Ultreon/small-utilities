package com.ultreon.mods.smallutilities.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ExtraHud extends GuiComponent implements IIngameOverlay {
    private static final ResourceLocation TEXT_FRAME = SmallUtilities.res("textures/gui/hud/text_bg.png");

    private static final Font font = Minecraft.getInstance().font;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
//        LocalPlayer player = Minecraft.getInstance().player;
//        if (player == null) return;
//
//        int x = player.getBlockX();
//        int y = player.getBlockY();
//        int z = player.getBlockZ();
//
//        drawTextFrame(poseStack, "Location: " + x + ", " + y + ", " + z, 2, 2);
    }

    public static void drawTextFrame(PoseStack pose, String text, int x, int y) {
//        drawTextFrame(pose, text, x, y, 0xffffff);
    }

    public static void drawTextFrame(PoseStack pose, String text, int x, int y, int color) {
//        drawTextFrame(pose, text, x, y, color, 0);
    }

    public static void drawTextFrame(PoseStack pose, String text, int x, int y, int color, int extraSpace) {
//        RenderSystem.setShaderTexture(0, TEXT_FRAME);
//        int width = font.width(text) + extraSpace + 2;
//        blit(pose, x, y, 3, 15, 0, 0, 3, 15, 23, 15);
//        blit(pose, x + 3, y, width, 15, 3, 0, 15, 15, 23, 15);
//        blit(pose, x + 3 + width, y, 3, 15, 20, 0, 3, 15, 23, 15);
//
//        if (extraSpace <= 0) font.draw(pose, text, x + 4, y + 4, color);
//        else font.draw(pose, text, x + 4 + extraSpace + 2, y + 4, color);
    }
}
