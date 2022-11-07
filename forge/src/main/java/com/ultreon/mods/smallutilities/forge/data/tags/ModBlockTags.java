package com.ultreon.mods.smallutilities.forge.data.tags;

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
    public ModBlockTags(final DataGenerator pGenerator, @Nullable final ExistingFileHelper existingFileHelper) {
        super(pGenerator, SmallUtilities.MOD_ID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return "Small Utilities - Block Tags";
    }

    @Override
    protected void addTags() {
        final TagAppender<Block> mineableWithPickaxe = this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.FUTURE_DASHBOARD.get())
                .add(ModBlocks.LAPTOP.get())
                .add(ModBlocks.IRON_GRID_PLATE.get())
                .add(ModBlocks.TRASH_CAN.get());
        final TagAppender<Block> needsStoneTool = this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.FUTURE_DASHBOARD.get())
                .add(ModBlocks.LAPTOP.get())
                .add(ModBlocks.IRON_GRID_PLATE.get())
                .add(ModBlocks.TRASH_CAN.get());

        for (final FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            mineableWithPickaxe.add(block);
            needsStoneTool.add(block);
        }

        for (final CoffeeTableBlock block : ModBlocks.getAllCoffeeTables().toList()) {
            this.tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(block);
        }
        for (final TableBlock block : ModBlocks.getAllTables().toList()) {
            this.tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(block);
        }
    }
}
