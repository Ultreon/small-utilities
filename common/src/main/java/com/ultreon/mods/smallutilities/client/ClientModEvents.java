package com.ultreon.mods.smallutilities.client;

import com.ultreon.mods.smallutilities.client.gui.screen.TrashCanScreen;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import com.ultreon.mods.smallutilities.init.ModMenus;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;

public class ClientModEvents {
    public static void initClient() {
        ClientLifecycleEvent.CLIENT_SETUP.register(ClientModEvents::clientSetup);
    }

    /**
     * Client side setup handling.
     * Handles only things from the client side.
     *
     * @param event the fml client setup event.
     * @author Qboi123
     * @since 1.0.0
     */
    public static void clientSetup(final Minecraft event) {
        // Do some client setup things
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.IRON_GRID_PLATE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.FUTURE_DASHBOARD.get());

        // Register block entity renderers.
        MenuRegistry.registerScreenFactory(ModMenus.TRASH_CAN.get(), TrashCanScreen::new);
    }

}
