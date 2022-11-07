package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModStats {
    private static final DeferredRegister<ResourceLocation> REGISTER = DeferredRegister.create(SmallUtilities.MOD_ID, Registry.CUSTOM_STAT_REGISTRY);
    public static final RegistrySupplier<ResourceLocation> OPEN_TRASH_CAN = REGISTER.register("open_trash_can", () -> new ResourceLocation(SmallUtilities.MOD_ID, "open_trash_can"));

    public static void register() {
        REGISTER.register();
    }
}
