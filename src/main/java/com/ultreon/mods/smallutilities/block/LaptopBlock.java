package com.ultreon.mods.smallutilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class LaptopBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLOSED = BooleanProperty.create("closed");

    private static final VoxelShape SHAPE_NORTH = Block.box(1, 0, 1, 15, 2, 11);
    private static final VoxelShape SHAPE_EAST = Block.box(5, 0, 1, 15, 2, 15);
    private static final VoxelShape SHAPE_SOUTH = Block.box(1, 0, 5, 15, 2, 15);
    private static final VoxelShape SHAPE_WEST = Block.box(1, 0, 1, 11, 2, 15);

    public LaptopBlock(Properties properties) {
        super(properties.instabreak());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CLOSED, true));
    }

    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return canSupportRigidBlock(pLevel, blockpos) || canSupportCenter(pLevel, blockpos, Direction.UP);
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @NotNull
    @Override
    public InteractionResult use(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pState.getValue(CLOSED)) {
            pLevel.setBlock(pPos, pState.setValue(CLOSED, false), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
        } else {
            pLevel.setBlock(pPos, pState.setValue(CLOSED, true), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
        }
        this.playSound(pPlayer, pLevel, pPos, !pState.getValue(CLOSED));
        return InteractionResult.SUCCESS;
    }

    protected void playSound(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, boolean pIsOpened) {
        if (pIsOpened) {
            int i = this.material == Material.METAL ? 1037 : 1007;
            pLevel.levelEvent(pPlayer, i, pPos, 0);
        } else {
            int j = this.material == Material.METAL ? 1036 : 1013;
            pLevel.levelEvent(pPlayer, j, pPos, 0);
        }

        pLevel.gameEvent(pPlayer, pIsOpened ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
    }

    /**
     * Returns the block-state with the given rotation from the passed block-state. If inapplicable, returns the passed
     * block-state.
     * possible. Implementing/overriding is fine.
     */
    @NotNull
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    /**
     * Returns the block-state with the given mirror of the passed block-state. If inapplicable, returns the passed
     * block-state.
     * possible. Implementing/overriding is fine.
     */
    @NotNull
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, CLOSED);
    }
}
