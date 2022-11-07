package com.ultreon.mods.smallutilities.init;

import com.google.common.collect.Streams;
import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.chars.Char2ReferenceArrayMap;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ModBlocks {
    private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(SmallUtilities.MOD_ID, Registry.BLOCK_REGISTRY);
    private static final List<RegistrySupplier<CoffeeTableBlock>> COFFEE_TABLES = new ArrayList<>();
    private static final List<RegistrySupplier<TableBlock>> TABLES = new ArrayList<>();

    public static final RegistrySupplier<IronGridPlateBlock> IRON_GRID_PLATE = REGISTER.register("iron_grid_plate", () -> new IronGridPlateBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
            .sound(SoundType.METAL)
            .noOcclusion()));
    public static final RegistrySupplier<LaptopBlock> LAPTOP = REGISTER.register("laptop", () -> new LaptopBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE)
            .lightLevel(value -> value.getValue(LaptopBlock.CLOSED) ? 4 : 0)
            .sound(SoundType.METAL)
            .strength(5f, 6f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));
    public static final RegistrySupplier<FutureDashboardBlock> FUTURE_DASHBOARD = REGISTER.register("future_dashboard", () -> new FutureDashboardBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE)
            .sound(SoundType.METAL)
            .strength(5f, 6f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));
    private static final Char2ReferenceArrayMap<RegistrySupplier<FutureDashboardBlock>> FUTURE_DASHBOARD_LETTERS = new Char2ReferenceArrayMap<>();

    static {
        for (char c = 'a'; 'z' >= c; c++) {
            final RegistrySupplier<FutureDashboardBlock> blockReg = REGISTER.register("future_dashboard_%s".formatted(c), () -> new FutureDashboardBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE)
                    .sound(SoundType.METAL)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));
            FUTURE_DASHBOARD_LETTERS.put(c, blockReg);
            ModItems.REGISTER.register("future_dashboard_%s".formatted(c), () -> new BlockItem(blockReg.get(), new Item.Properties()
                    .tab(CreativeModeTab.TAB_DECORATIONS)));
        }
    }

    public static final RegistrySupplier<TrashCanBlock> TRASH_CAN = REGISTER.register("trash_can", () -> new TrashCanBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
            .sound(SoundType.METAL)
            .noOcclusion()));
    public static final RegistrySupplier<CoffeeTableBlock> OAK_COFFEE_TABLE = registerCoffeeTable("oak");
    public static final RegistrySupplier<CoffeeTableBlock> BIRCH_COFFEE_TABLE = registerCoffeeTable("birch");
    public static final RegistrySupplier<CoffeeTableBlock> SPRUCE_COFFEE_TABLE = registerCoffeeTable("spruce");
    public static final RegistrySupplier<CoffeeTableBlock> JUNGLE_COFFEE_TABLE = registerCoffeeTable("jungle");
    public static final RegistrySupplier<CoffeeTableBlock> ACACIA_COFFEE_TABLE = registerCoffeeTable("acacia");
    public static final RegistrySupplier<CoffeeTableBlock> DARK_OAK_COFFEE_TABLE = registerCoffeeTable("dark_oak");
    public static final RegistrySupplier<TableBlock> OAK_TABLE = registerTable("oak");
    public static final RegistrySupplier<TableBlock> BIRCH_TABLE = registerTable("birch");
    public static final RegistrySupplier<TableBlock> SPRUCE_TABLE = registerTable("spruce");
    public static final RegistrySupplier<TableBlock> JUNGLE_TABLE = registerTable("jungle");
    public static final RegistrySupplier<TableBlock> ACACIA_TABLE = registerTable("acacia");
    public static final RegistrySupplier<TableBlock> DARK_OAK_TABLE = registerTable("dark_oak");

    private static RegistrySupplier<CoffeeTableBlock> registerCoffeeTable(final String name) {
        final RegistrySupplier<CoffeeTableBlock> object = REGISTER.register(name + "_coffee_table", () -> new CoffeeTableBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
                .strength(2f)
                .sound(SoundType.WOOD)
                .noOcclusion()));
        COFFEE_TABLES.add(object);

        ModItems.REGISTER.register(name + "_coffee_table", () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        return object;
    }

    private static RegistrySupplier<TableBlock> registerTable(final String name) {
        final RegistrySupplier<TableBlock> object = REGISTER.register(name + "_table", () -> new TableBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
                .strength(2f)
                .sound(SoundType.WOOD)
                .noOcclusion()));
        TABLES.add(object);

        ModItems.REGISTER.register(name + "_table", () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        return object;
    }

    public static void register() {
        REGISTER.register();
    }

    @SuppressWarnings("UnstableApiUsage")
    public static Stream<Block> getAllBlocks() {
        return Streams.stream(REGISTER.iterator()).map(RegistrySupplier::get);
    }

    public static Stream<CoffeeTableBlock> getAllCoffeeTables() {
        return COFFEE_TABLES.stream().map(RegistrySupplier::get);
    }

    public static Stream<TableBlock> getAllTables() {
        return TABLES.stream().map(RegistrySupplier::get);
    }

    public static Stream<FutureDashboardBlock> getAllFutureDashboards() {
        return FUTURE_DASHBOARD_LETTERS.values().stream().map(RegistrySupplier::get);
    }

    public static FutureDashboardBlock getFutureDashboard(final char letter) {
        return FUTURE_DASHBOARD_LETTERS.get(letter).get();
    }
}
