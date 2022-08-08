package com.ultreon.mods.smallutilities;

import com.ultreon.mods.smallutilities.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * The main mod class.
 * <p>
 * This class is the entry point for the mod. It is responsible for initializing the mod and registering all of the
 * mod's events.
 * </p>
 *
 * @author Qboi123
 * @since 1.0.0
 */
@Mod(SmallUtilities.MOD_ID)
public class SmallUtilities {
    public static final String MOD_ID = "smallutils";
    public static final Logger LOGGER = LoggerFactory.getLogger("SmallUtils");

    private static final List<Block> CUTTER_BLOCKS = new ArrayList<>();

    /**
     * Small Utilities is a mod that adds a few small utilities to Minecraft.
     *
     * @author Qboi123
     * @since 1.0.0
     */
    public SmallUtilities() {
        final IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEvents = MinecraftForge.EVENT_BUS;

        // Register the setup method for modloading
        modEvents.addListener(this::commonSetup);
        // Register the enqueueIMC method for modloading
        modEvents.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modEvents.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        forgeEvents.register(this);

        ModBlocks.register(modEvents);
        ModItems.register(modEvents);
        ModMenus.register(modEvents);
        ModBlockEntities.register(modEvents);
        ModStats.register(modEvents);
        ModTags.init();

        Config.init();
    }

    /**
     * Create a resource location from the mod's id and the given path.
     *
     * @param path The path to the resource.
     * @return The resource location.
     * @author Qboi123
     * @since 1.0.0-dev4
     */
    public static ResourceLocation res(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Common side setup handler.
     * Handles things from both client and server side.
     *
     * @param event the fml common setup event.
     * @author Qboi123
     * @since 1.0.0
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Do some common setup things
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Send hello world to itself.
        InterModComms.sendTo(MOD_ID, "hello_world", () -> {
            LOGGER.info("Sending hello world to myself");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // Receive a message from itself, or other mods that sends in IMC messages.
        event.getIMCStream().forEachOrdered(this::imcMessageHandle);
    }

    private void imcMessageHandle(final InterModComms.IMCMessage action) {
        switch (action.method().toLowerCase(Locale.ROOT)) {
            case "hello_world" -> this.imcHelloWorld(action);
            case "register:cutter_block" -> this.imcRegisterCutterBlock(action);
        }
    }

    private void imcHelloWorld(final InterModComms.IMCMessage action) {
        LOGGER.info("Hello, " + action.senderModId());
    }

    private void imcRegisterCutterBlock(final InterModComms.IMCMessage action) {
        final Object o = action.messageSupplier().get();
        if (o instanceof Block) {
            CUTTER_BLOCKS.add((Block) o);
        } else {
            LOGGER.error("Invalid message received from " + action.senderModId() + ": " + o);
        }
    }

    /**
     * Server starting handler.
     * Handles the server starting event.
     *
     * @param event the server starting event.
     * @author Qboi123
     * @since 1.0.0
     */
    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {
        // Do some server starting things

        LOGGER.info("HELLO from server starting");
    }

    public void registerCutterBlock(final Block block) {
        CUTTER_BLOCKS.add(block);
    }

    public List<Block> getCutterBlocks() {
        return Collections.unmodifiableList(CUTTER_BLOCKS);
    }
}
