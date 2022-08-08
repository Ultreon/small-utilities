package com.ultreon.mods.smallutilities.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeleteConfirmationScreen extends Screen {
    private final TrashCanScreen parent;
    private final Component line1;
    private final Component line2;
    private int buttonCooldown;
    private Button yesButton;
    private Button noButton;

    public DeleteConfirmationScreen(final TrashCanScreen parent) {
        this(parent, new TranslatableComponent("container.smallutils.trash_can.delete_confirmation.description", parent.getMenu().getTrashItemCount()), parent.getMenu().calculateMaxXp() / parent.getMenu().getXpReduction(), parent.getMenu().calculateMaxXp());
    }

    public DeleteConfirmationScreen(final TrashCanScreen parent, final Component line1, final Component line2) {
        super(new TranslatableComponent("container.smallutils.trash_can.delete_confirmation.title"));
        this.parent = parent;
        this.line1 = line1;
        this.line2 = line2;
    }

    public DeleteConfirmationScreen(final TrashCanScreen parent, final TranslatableComponent description, final int minXp, final int maxXp) {
        this(parent, description, new TranslatableComponent("container.smallutils.trash_can.delete_confirmation.description1", minXp, maxXp));
    }

    @Override
    protected void init() {
        super.init();

        final int midX = width / 2;

        buttonCooldown = 60;

        yesButton = addRenderableWidget(new Button(midX - 105, 0, 100, 20, CommonComponents.GUI_YES, (button) -> {
            assert null != this.minecraft;
            assert null != this.minecraft.player;
            if (parent.getMenu().clickMenuButton(minecraft.player, 0)) {
                assert null != this.minecraft.gameMode;
                minecraft.gameMode.handleInventoryButtonClick(parent.getMenu().containerId, 0);
            }
            minecraft.popGuiLayer();
        }));

        noButton = addRenderableWidget(new Button(midX + 5, 0, 100, 20, CommonComponents.GUI_NO, (button) -> {
            if (null != this.minecraft) {
                minecraft.popGuiLayer();
            }
        }));

        yesButton.active = false;
        noButton.active = false;
    }

    @Override
    public void tick() {
        super.tick();

        if (0 < this.buttonCooldown) {
            buttonCooldown--;
        } else {
            yesButton.active = true;
            noButton.active = true;
        }
    }

    @Override
    public void render(@NotNull final PoseStack pose, final int mouseX, final int mouseY, final float partialTick) {
        renderBackground(pose, 0);

        super.render(pose, mouseX, mouseY, partialTick);

        final int midX = width / 2;
        final int midY = height / 2 - 60;

        final int titleY = midY - 40;
        final int textY = midY + 20;
        final int textY1 = midY + 32;

        pose.pushPose();
        {
            pose.scale(2f, 2f, 1f);

            GuiComponent.drawCenteredString(pose, font, title, midX / 2, titleY, 0xFFFFFF);
        }
        pose.popPose();

        GuiComponent.drawCenteredString(pose, font, line1, midX, textY, 0xFFFFFF);
        GuiComponent.drawCenteredString(pose, font, line2, midX, textY1, 0xFFFFFF);

        final Container container = parent.getMenu().getContainer();
        int y = midY + 50;
        for (int i = 0; i < container.getContainerSize(); i++) {
            final ItemStack item = container.getItem(i);
            if (item.isEmpty()) {
                continue;
            }

            final TextComponent textComponent = new TextComponent(item.getCount() + "x ");
            final MutableComponent name = (new TextComponent(""))
                    .append(item.getHoverName())
                    .withStyle(style -> item.getRarity()
                            .getStyleModifier()
                            .apply(style)
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(item))));
            textComponent.append(new TextComponent("").append(name));

            GuiComponent.drawCenteredString(pose, font, textComponent, midX, y, 0xFFFFFF);

            y += font.lineHeight;
        }

        y += font.lineHeight * 2;

        yesButton.y = y;
        noButton.y = y;
    }

    public Component getLine1() {
        return line1;
    }

    public Component getLine2() {
        return line2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
