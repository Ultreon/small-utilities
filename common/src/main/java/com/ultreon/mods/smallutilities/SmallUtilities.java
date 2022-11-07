package com.ultreon.mods.smallutilities;

import com.ultreon.mods.smallutilities.client.ClientModEvents;
import com.ultreon.mods.smallutilities.hooks.TickHooks;
import com.ultreon.mods.smallutilities.init.*;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    }

    @SuppressWarnings("Convert2MethodRef")
    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModMenus.register();
        ModBlockEntities.register();
        ModStats.register();
        ModTags.init();

        Config.init();

        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
            ClientModEvents.initClient();
        });

        TickHooks.init();

        LifecycleEvent.SETUP.register(SmallUtilities::commonSetup);
        LifecycleEvent.SERVER_STARTING.register(SmallUtilities::onServerStarting);
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
     * @author Qboi123
     * @since 1.0.0
     */
    private static void commonSetup() {
        // Do some common setup things
    }

    /**
     * Server starting handler.
     * Handles the server starting event.
     *
     * @param server the server starting event.
     * @author Qboi123
     * @since 1.0.0
     */
    public static void onServerStarting(final MinecraftServer server) {
        // Do some server starting things
        LOGGER.info("HELLO from server starting");
    }

    public static void registerCutterBlock(final Block block) {
        CUTTER_BLOCKS.add(block);
    }

    public List<Block> getCutterBlocks() {
        return Collections.unmodifiableList(CUTTER_BLOCKS);
    }
}
