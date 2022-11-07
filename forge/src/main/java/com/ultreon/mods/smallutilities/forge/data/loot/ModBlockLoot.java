package com.ultreon.mods.smallutilities.forge.data.loot;

import com.ultreon.mods.smallutilities.init.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ModBlockLoot extends BlockLoot {
    public ModBlockLoot() {
    }

    @Override
    protected void addTables() {
        ModBlocks.getAllBlocks().forEachOrdered(this::dropSelf);
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.getAllBlocks().toList();
    }
}
