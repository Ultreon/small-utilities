package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.inventory.menu.TrashCanMenu;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(SmallUtilities.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<TrashCanMenu>> TRASH_CAN = REGISTER.register("trash_can", () -> new MenuType<>(TrashCanMenu::new));

    public static void register() {
        REGISTER.register();
    }
}
