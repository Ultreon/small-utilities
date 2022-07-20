package com.ultreon.mods.smallutilities.data.tags;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.CoffeeTableBlock;
import com.ultreon.mods.smallutilities.block.FutureDashboardBlock;
import com.ultreon.mods.smallutilities.block.TableBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, SmallUtilities.MOD_ID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return "Small Utilities - Block Tags";
    }

    @Override
    protected void addTags() {
        TagAppender<Block> mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.FUTURE_DASHBOARD.get())
                .add(ModBlocks.LAPTOP.get())
                .add(ModBlocks.IRON_GRID_PLATE.get())
                .add(ModBlocks.TRASH_CAN.get());
        TagAppender<Block> needsStoneTool = tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.FUTURE_DASHBOARD.get())
                .add(ModBlocks.LAPTOP.get())
                .add(ModBlocks.IRON_GRID_PLATE.get())
                .add(ModBlocks.TRASH_CAN.get());

        for (FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            mineableWithPickaxe.add(block);
            needsStoneTool.add(block);
        }

        for (CoffeeTableBlock block : ModBlocks.getAllCoffeeTables().toList()) {
            tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(block);
        }
        for (TableBlock block : ModBlocks.getAllTables().toList()) {
            tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(block);
        }
    }
}
