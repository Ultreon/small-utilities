package com.ultreon.mods.smallutilities.fabric;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigImpl {
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client, ForgeConfigSpec.Builder common) {
        ModLoadingContext.registerConfig(SmallUtilities.MOD_ID, ModConfig.Type.SERVER, server.build());
        ModLoadingContext.registerConfig(SmallUtilities.MOD_ID, ModConfig.Type.CLIENT, client.build());
        ModLoadingContext.registerConfig(SmallUtilities.MOD_ID, ModConfig.Type.COMMON, common.build());
    }
}
