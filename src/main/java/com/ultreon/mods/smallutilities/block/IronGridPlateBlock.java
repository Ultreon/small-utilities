package com.ultreon.mods.smallutilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class IronGridPlateBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TOP = Block.box(0, 14, 0, 16, 16, 16);
    private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 2, 16);

    public IronGridPlateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, Half.BOTTOM).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>(pItems);
        for (int i = 0, itemStacksSize = itemStacks.size(); i < itemStacksSize; i++) {
            ItemStack stack = itemStacks.get(i);
            if (stack.getItem() == Items.IRON_BARS) {
                pItems.add(i + 1, new ItemStack(this));
                return;
            }
        }
        super.fillItemCategory(pTab, pItems);
    }

    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return pType == PathComputationType.WATER ? pState.getValue(WATERLOGGED) : false;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(HALF)) {
            case TOP -> TOP;
            case BOTTOM -> BOTTOM;
        };
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getClickedFace();
        if (!pContext.replacingClickedOnBlock() && direction.getAxis().isHorizontal()) {
            blockstate = blockstate.setValue(HALF, pContext.getClickLocation().y - (double) pContext.getClickedPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM);
        } else {
            blockstate = blockstate.setValue(HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
        }

        return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, WATERLOGGED);
    }

    @NotNull
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @NotNull
    public BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}
