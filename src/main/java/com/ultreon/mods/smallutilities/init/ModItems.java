package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, SmallUtilities.MOD_ID);

    public static final RegistryObject<BlockItem> IRON_GRID_PLATE = REGISTER.register("iron_grid_plate", () -> new BlockItem(ModBlocks.IRON_GRID_PLATE.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> LAPTOP = REGISTER.register("laptop", () -> new BlockItem(ModBlocks.LAPTOP.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)
            .stacksTo(8)));
    public static final RegistryObject<BlockItem> FUTURE_DASHBOARD = REGISTER.register("future_dashboard", () -> new BlockItem(ModBlocks.FUTURE_DASHBOARD.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> TRASH_CAN = REGISTER.register("trash_can", () -> new BlockItem(ModBlocks.TRASH_CAN.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
