package com.ultreon.mods.smallutilities;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    public static final ForgeConfigSpec.DoubleValue STONE_CUTTER_DAMAGE;
    public static final ForgeConfigSpec.IntValue STONE_CUTTER_DAMAGE_DELAY;
    public static final ForgeConfigSpec.BooleanValue ENABLE_TRASH_CAN;

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    static {
        SERVER_BUILDER.comment("Stone Cutter Settings").push("stone_cutter");
        STONE_CUTTER_DAMAGE = SERVER_BUILDER.comment("Stone Cutter Damage").defineInRange("damage", 3.0, 0.0, 10.0);
        STONE_CUTTER_DAMAGE_DELAY = SERVER_BUILDER.comment("Stone Cutter Damage Delay").defineInRange("damageDelay", 10, 0, 200);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Features").push("features");
        ENABLE_TRASH_CAN = SERVER_BUILDER.comment("Enable Trash Can").define("enable_trash_can", true);
        SERVER_BUILDER.pop();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }
}
