package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.inventory.menu.TrashCanMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, SmallUtilities.MOD_ID);

    public static final RegistryObject<MenuType<TrashCanMenu>> TRASH_CAN = REGISTER.register("trash_can", () -> new MenuType<>(TrashCanMenu::new));

    public static void register(final IEventBus modEvents) {
        REGISTER.register(modEvents);
    }
}
