package com.ultreon.mods.smallutilities.block;

import com.ultreon.mods.smallutilities.block.entity.PostboxBlockEntity;
import com.ultreon.mods.smallutilities.init.ModStats;
import com.ultreon.mods.smallutilities.inventory.PlayerPostboxContainer;
import com.ultreon.mods.smallutilities.post.PostboxManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class PostboxBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLOSED = BooleanProperty.create("closed");

    private static final VoxelShape SHAPE_NORTH = Stream.of(
            Block.box(7, 0, 7, 9, 9, 9),
            Block.box(5, 9, 4, 6, 13, 12),
            Block.box(10, 9, 4, 11, 13, 12),
            Block.box(5, 13, 4, 11, 15, 12),
            Block.box(6, 9, 4, 10, 10, 12),
            Block.box(6, 10, 11, 10, 13, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_EAST = Stream.of(
            Block.box(7, 0, 7, 9, 9, 9),
            Block.box(4, 9, 5, 12, 13, 6),
            Block.box(4, 9, 10, 12, 13, 11),
            Block.box(4, 13, 5, 12, 15, 11),
            Block.box(4, 9, 6, 12, 10, 10),
            Block.box(4, 10, 6, 5, 13, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_SOUTH = Stream.of(
            Block.box(7, 0, 7, 9, 9, 9),
            Block.box(10, 9, 4, 11, 13, 12),
            Block.box(5, 9, 4, 6, 13, 12),
            Block.box(5, 13, 4, 11, 15, 12),
            Block.box(6, 9, 4, 10, 10, 12),
            Block.box(6, 10, 4, 10, 13, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_WEST = Stream.of(
            Block.box(7, 0, 7, 9, 9, 9),
            Block.box(4, 9, 10, 12, 13, 11),
            Block.box(4, 9, 5, 12, 13, 6),
            Block.box(4, 13, 5, 12, 15, 11),
            Block.box(4, 9, 6, 12, 10, 10),
            Block.box(11, 10, 6, 12, 13, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public PostboxBlock(Properties properties) {
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
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult pHit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PostboxBlockEntity enderChestBlockEntity)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        BlockPos blockAbove = pos.above();
        if (level.getBlockState(blockAbove).isRedstoneConductor(level, blockAbove)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        PlayerPostboxContainer container = PostboxManager.INSTANCE.get(player);
        container.setActivePostbox(enderChestBlockEntity);
        player.openMenu(new SimpleMenuProvider((id, inventory, p) -> ChestMenu.threeRows(id, inventory, container), getName()));
        player.awardStat(ModStats.OPEN_POSTBOX.get());
        PiglinAi.angerNearbyPiglins(player, true);
        return InteractionResult.CONSUME;
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
