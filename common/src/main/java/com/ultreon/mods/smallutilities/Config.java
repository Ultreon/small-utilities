package com.ultreon.mods.smallutilities;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraftforge.common.ForgeConfigSpec;

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

    @ExpectPlatform
    public static void init(final ForgeConfigSpec.Builder server, final ForgeConfigSpec.Builder client, final ForgeConfigSpec.Builder common) {
        throw new AssertionError("Not initialized");
    }

    public static void init() {
        init(SERVER_BUILDER, CLIENT_BUILDER, COMMON_BUILDER);
    }
}
