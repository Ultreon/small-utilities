package com.ultreon.mods.smallutilities.forge;

import com.ultreon.mods.smallutilities.SmallUtilities;
import dev.architectury.platform.forge.EventBuses;
import net.examplemod.ExampleMod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

@Mod(SmallUtilities.MOD_ID)
public class SmallUtilitiesForge {
    public SmallUtilitiesForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SmallUtilities.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SmallUtilities.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Send hello world to itself.
        InterModComms.sendTo(SmallUtilities.MOD_ID, "hello_world", () -> {
            SmallUtilities.LOGGER.info("Sending hello world to myself");
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
        SmallUtilities.LOGGER.info("Hello, " + action.senderModId());
    }

    private void imcRegisterCutterBlock(final InterModComms.IMCMessage action) {
        final Object o = action.messageSupplier().get();
        if (o instanceof Block) {
            SmallUtilities.registerCutterBlock((Block) o);
        } else {
            SmallUtilities.LOGGER.error("Invalid message received from " + action.senderModId() + ": " + o);
        }
    }

}
