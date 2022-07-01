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

public class CoffeeTableBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.join(Block.box(0, 5, 0, 16, 7, 16), Shapes.join(Block.box(14, 0, 0, 16, 5, 2), Shapes.join(Block.box(0, 0, 0, 2, 5, 2), Shapes.join(Block.box(14, 0, 14, 16, 5, 16), Block.box(0, 0, 14, 2, 5, 16), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);

    public CoffeeTableBlock(Properties properties) {
        super(properties.instabreak());
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }
}
