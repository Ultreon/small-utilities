package com.ultreon.mods.smallutilities.block;

import com.ultreon.mods.smallutilities.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class TrashCanBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public TrashCanBlock(Properties properties) {
        super(properties.instabreak());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)/*.setValue(CLOSED, true)*/);
    }

    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return canSupportRigidBlock(pLevel, blockpos) || canSupportCenter(pLevel, blockpos, Direction.UP);
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
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
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.isEmpty()) return InteractionResult.PASS;
        if (itemInHand.is(ModTags.Items.TRASH_BLACKLIST)) return InteractionResult.FAIL;

        if (pPlayer.isCrouching()) itemInHand.setCount(0);
        else itemInHand.shrink(1);

        if (pPlayer.getRandom().nextInt(30) == 0)
            pLevel.addFreshEntity(new ExperienceOrb(pLevel, pPos.getX() + 0.5, pPos.getY() + 1.5, pPos.getZ() + 0.5, 1));

        pLevel.playSound(pPlayer, pPos, SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 1.0F, 1.0F);
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
//        pBuilder.add(FACING, CLOSED);
        pBuilder.add(FACING);
    }
}
