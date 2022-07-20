package com.ultreon.mods.smallutilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FutureDashboardBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 11, 16, 10.1, 16),
            Block.box(0, 0, 5, 16, 12.6, 11),
            Block.box(0, 0, 0, 16, 16, 5)
    );
    private static final VoxelShape SHAPE_EAST = Shapes.or(
            Block.box(0, 0, 0, 5, 10.1, 16),
            Block.box(5, 0, 0, 11, 12.6, 16),
            Block.box(11, 0, 0, 16, 16, 16)
    );
    private static final VoxelShape SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 0, 16, 10.1, 5),
            Block.box(0, 0, 5, 16, 12.6, 11),
            Block.box(0, 0, 11, 16, 16, 16)
    );
    private static final VoxelShape SHAPE_WEST = Shapes.or(
            Block.box(11, 0, 0, 16, 10.1, 16),
            Block.box(5, 0, 0, 11, 12.6, 16),
            Block.box(0, 0, 0, 5, 16, 16)
    );

    public FutureDashboardBlock(Properties properties) {
        super(properties);

        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + pState.getValue(FACING));
        };
    }

    @NotNull
    public VoxelShape getOcclusionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return Shapes.empty();
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
