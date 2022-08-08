package com.ultreon.mods.smallutilities.client;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.client.gui.overlay.ExtraHud;
import com.ultreon.mods.smallutilities.client.gui.screen.TrashCanScreen;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import com.ultreon.mods.smallutilities.init.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SmallUtilities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    /**
     * Client side setup handling.
     * Handles only things from the client side.
     *
     * @param event the fml client setup event.
     * @author Qboi123
     * @since 1.0.0
     */
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        // Do some client setup things
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_GRID_PLATE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FUTURE_DASHBOARD.get(), RenderType.cutout());

        // Register overlays.
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, SmallUtilities.MOD_ID + ":extra_hud", new ExtraHud());

        // Register block entity renderers.
        MenuScreens.register(ModMenus.TRASH_CAN.get(), TrashCanScreen::new);
    }

}
