package com.ultreon.mods.smallutilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TableBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.join(Block.box(0, 14, 0, 16, 16, 16), Shapes.join(Block.box(14, 0, 0, 16, 14, 2), Shapes.join(Block.box(0, 0, 0, 2, 14, 2), Shapes.join(Block.box(14, 0, 14, 16, 14, 16), Block.box(0, 0, 14, 2, 14, 16), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);

    public TableBlock(final Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull final BlockState pState, @NotNull final BlockGetter pLevel, @NotNull final BlockPos pPos, @NotNull final CollisionContext pContext) {
        return SHAPE;
    }
}
