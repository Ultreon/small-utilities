package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStats {
    private static final DeferredRegister<ResourceLocation> REGISTER = DeferredRegister.create(Registry.CUSTOM_STAT_REGISTRY, SmallUtilities.MOD_ID);
    public static final RegistryObject<ResourceLocation> OPEN_TRASH_CAN = REGISTER.register("open_trash_can", () -> new ResourceLocation(SmallUtilities.MOD_ID, "open_trash_can"));

    public static void register(final IEventBus modEvents) {
        REGISTER.register(modEvents);
    }
}
