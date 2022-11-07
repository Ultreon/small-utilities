package com.ultreon.mods.smallutilities.forge.data.client;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.IronGridPlateBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(final DataGenerator generator, final ExistingFileHelper exFileHelper) {
        super(generator, SmallUtilities.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.getAllBlocks().forEachOrdered(block -> {
            if (block.asItem() != Items.AIR) {
                if (block instanceof IronGridPlateBlock) {
                    this.withExistingParent(Objects.requireNonNull(block.getRegistryName()).toString(), this.modLoc("block/iron_grid_plate_bottom"));
                } else {
                    this.blockItem(block);
                }
            }
        });
    }

    private void blockItem(final Block block) {
        this.withExistingParent(Objects.requireNonNull(block.asItem().getRegistryName()).getPath(), this.modLoc("block/" + Objects.requireNonNull(block.getRegistryName()).getPath()));
    }
}
