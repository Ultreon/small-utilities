package com.ultreon.mods.smallutilities.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.inventory.menu.TrashCanMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TrashCanScreen extends AbstractContainerScreen<TrashCanMenu> {
    private static final ResourceLocation TEXTURE = SmallUtilities.res("textures/gui/container/trash_can.png");
    private static final Component TRASH_TEXT = Component.translatable("container.smallutils.trash_can.trash");

    public TrashCanScreen(final TrashCanMenu menu, final Inventory inventory, final Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 133;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        final int x = (this.width - this.imageWidth) / 2;
        final int y = (this.height - this.imageHeight) / 2;

        super.init();

        this.addRenderableWidget(new Button(x + 99, y + 16, 70, 20, TRASH_TEXT, (button) -> {
            if (null != this.minecraft) {
                this.minecraft.setScreen(new DeleteConfirmationScreen(this));
            }
        }));
    }

    @Override
    protected void renderBg(@NotNull final PoseStack poseStack, final float pPartialTick, final int pMouseX, final int pMouseY) {
        if (!(Objects.requireNonNull(this.minecraft).screen instanceof DeleteConfirmationScreen)) {
            this.renderBackground(poseStack);
        }

        RenderSystem.setShaderTexture(0, TEXTURE);
        final int x = (this.width - this.imageWidth) / 2;
        final int y = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
