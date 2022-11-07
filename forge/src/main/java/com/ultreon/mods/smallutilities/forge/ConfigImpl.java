package com.ultreon.mods.smallutilities.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigImpl {
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client, ForgeConfigSpec.Builder common) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, server.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, client.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, common.build());
    }
}
