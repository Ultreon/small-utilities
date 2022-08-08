package com.ultreon.mods.smallutilities.block;

import com.ultreon.mods.smallutilities.block.entity.TrashCanBlockEntity;
import com.ultreon.mods.smallutilities.init.ModBlockEntities;
import com.ultreon.mods.smallutilities.init.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TrashCanBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    private final RegistryObject<BlockEntityType<TrashCanBlockEntity>> blockEntityType = ModBlockEntities.TRASH_CAN;

    public TrashCanBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    public boolean canSurvive(@NotNull final BlockState state, @NotNull final LevelReader level, final BlockPos pos) {
        final BlockPos blockpos = pos.below();
        return Block.canSupportRigidBlock(level, blockpos) || Block.canSupportCenter(level, blockpos, Direction.UP);
    }

    @NotNull
    public InteractionResult use(@NotNull final BlockState state, final Level level, @NotNull final BlockPos pos, @NotNull final Player player, @NotNull final InteractionHand hand, @NotNull final BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            final MenuProvider menuprovider = this.getMenuProvider(state, level, pos);
            if (null != menuprovider) {
                player.openMenu(menuprovider);
                player.awardStat(this.getOpenTrashCanStat());
                PiglinAi.angerNearbyPiglins(player, true);
            }

            return InteractionResult.CONSUME;
        }
    }

    public void neighborChanged(@NotNull final BlockState state, final Level level, @NotNull final BlockPos pos, @NotNull final Block block, @NotNull final BlockPos fromPos, final boolean isMoving) {
        final boolean flag = level.hasNeighborSignal(pos);
        if (!this.defaultBlockState().is(block) && flag != state.getValue(POWERED)) {
            if (flag && !state.getValue(POWERED)) {
                level.getBlockEntity(pos, ModBlockEntities.TRASH_CAN.get()).ifPresent(TrashCanBlockEntity::trash);
            }

            level.setBlock(pos, state.setValue(POWERED, flag), 2);
        }

    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull final BlockState state, final Level level, @NotNull final BlockPos pos) {
        final BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TrashCanBlockEntity) {
            return ((TrashCanBlockEntity) blockEntity);
        }
        return null;
    }

    @NotNull
    public BlockEntityType<TrashCanBlockEntity> blockEntityType() {
        return this.blockEntityType.get();
    }

    private ResourceLocation getOpenTrashCanStat() {
        return ModStats.OPEN_TRASH_CAN.get();
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull final BlockState state, @NotNull final BlockGetter level, @NotNull final BlockPos pos, @NotNull final CollisionContext context) {
        return SHAPE;
    }

    @NotNull
    public VoxelShape getOcclusionShape(@NotNull final BlockState state, @NotNull final BlockGetter level, @NotNull final BlockPos pos) {
        return Shapes.empty();
    }

    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final boolean flag = context.getLevel().hasNeighborSignal(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, flag);
    }

    /**
     * Returns the block-state with the given rotation from the passed block-state. If inapplicable, returns the passed
     * block-state.
     * possible. Implementing/overriding is fine.
     */
    @NotNull
    public BlockState rotate(final BlockState state, final Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the block-state with the given mirror of the passed block-state. If inapplicable, returns the passed
     * block-state.
     * possible. Implementing/overriding is fine.
     */
    @NotNull
    public BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull final BlockPos position, @NotNull final BlockState state) {
        return new TrashCanBlockEntity(position, state);
    }
}
