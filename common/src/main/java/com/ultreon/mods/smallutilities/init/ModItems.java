package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public final class ModItems {
    static final DeferredRegister<Item> REGISTER = DeferredRegister.create(SmallUtilities.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<BlockItem> IRON_GRID_PLATE = REGISTER.register("iron_grid_plate", () -> new BlockItem(ModBlocks.IRON_GRID_PLATE.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistrySupplier<BlockItem> LAPTOP = REGISTER.register("laptop", () -> new BlockItem(ModBlocks.LAPTOP.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)
            .stacksTo(8)));
    public static final RegistrySupplier<BlockItem> FUTURE_DASHBOARD = REGISTER.register("future_dashboard", () -> new BlockItem(ModBlocks.FUTURE_DASHBOARD.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistrySupplier<BlockItem> TRASH_CAN = REGISTER.register("trash_can", () -> new BlockItem(ModBlocks.TRASH_CAN.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_REDSTONE)));

    public static void register() {
        REGISTER.register();
    }
}
