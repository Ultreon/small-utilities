package com.ultreon.mods.smallutilities.fabric;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.examplemod.ExampleMod;
import net.fabricmc.api.ModInitializer;

public class SmallUtilitiesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SmallUtilities.init();
    }
}
