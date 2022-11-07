package com.ultreon.mods.smallutilities.forge.data.client;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.FutureDashboardBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModBlockStates extends BlockStateProvider {
    public ModBlockStates(final DataGenerator generator, final ExistingFileHelper exFileHelper) {
        super(generator, SmallUtilities.MOD_ID, exFileHelper);

    }

    @NotNull
    @Override
    public String getName() {
        return "Small Utilities - Block States and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        this.coffeeTable(ModBlocks.OAK_COFFEE_TABLE.get(), "oak");
        this.coffeeTable(ModBlocks.BIRCH_COFFEE_TABLE.get(), "birch");
        this.coffeeTable(ModBlocks.SPRUCE_COFFEE_TABLE.get(), "spruce");
        this.coffeeTable(ModBlocks.JUNGLE_COFFEE_TABLE.get(), "jungle");
        this.coffeeTable(ModBlocks.ACACIA_COFFEE_TABLE.get(), "acacia");
        this.coffeeTable(ModBlocks.DARK_OAK_COFFEE_TABLE.get(), "dark_oak");

        this.table(ModBlocks.OAK_TABLE.get(), "oak");
        this.table(ModBlocks.BIRCH_TABLE.get(), "birch");
        this.table(ModBlocks.SPRUCE_TABLE.get(), "spruce");
        this.table(ModBlocks.JUNGLE_TABLE.get(), "jungle");
        this.table(ModBlocks.ACACIA_TABLE.get(), "acacia");
        this.table(ModBlocks.DARK_OAK_TABLE.get(), "dark_oak");

        for (char c = 'a'; 'z' >= c; c++) {
            this.futureDashboard(ModBlocks.getFutureDashboard(c), Character.toString(c));
        }
    }

    private void coffeeTable(final Block block, final String type) {
        this.getVariantBuilder(block).forAllStates(state -> {
            final String name = Objects.requireNonNull(block.getRegistryName()).getPath();
            return ConfiguredModel.builder()
                    .modelFile(this.models()
                            .withExistingParent(name, this.modLoc("block/coffee_table"))
                            .texture("0", "minecraft:block/stripped_" + type + "_log")
                            .texture("particle", "minecraft:block/stripped_" + type + "_log")
                    )
                    .build();
        });
    }

    private void table(final Block block, final String type) {
        this.getVariantBuilder(block).forAllStates(state -> {
            final String name = Objects.requireNonNull(block.getRegistryName()).getPath();
            return ConfiguredModel.builder()
                    .modelFile(this.models()
                            .withExistingParent(name, this.modLoc("block/table"))
                            .texture("0", "minecraft:block/stripped_" + type + "_log")
                            .texture("particle", "minecraft:block/stripped_" + type + "_log")
                    )
                    .build();
        });
    }

    private void futureDashboard(final Block block, final String type) {
        this.getVariantBuilder(block).forAllStates(state -> {
            final String name = Objects.requireNonNull(block.getRegistryName()).getPath();
            final Direction direction = state.getValue(FutureDashboardBlock.FACING);
            return ConfiguredModel.builder()
                    .modelFile(this.models()
                            .withExistingParent(name, this.modLoc("block/future_dashboard"))
                            .texture("0", this.modLoc("block/future_dashboard_" + type))
                            .texture("particle", this.modLoc("block/future_dashboard_" + type))
                    )
                    .rotationY((int) direction.toYRot())
                    .build();
        });
    }
}
