package com.ultreon.mods.smallutilities.init;

import dev.architectury.registry.level.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public class ModGamerules {
    public static GameRules.Key<GameRules.BooleanValue> DO_EXPLOSIONS = GameRules.register("doExplosions", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));

    public static void register() {

    }
}
