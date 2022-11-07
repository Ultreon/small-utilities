package com.ultreon.mods.smallutilities.block.entity;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.init.ModBlockEntities;
import com.ultreon.mods.smallutilities.inventory.menu.TrashCanMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class TrashCanBlockEntity extends BaseContainerBlockEntity implements TickableBlockEntity {
    private static final Marker MARKER = MarkerFactory.getMarker("TRASH-CAN:BE");
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
//    private LazyOptional<IItemHandlerModifiable> chestHandler;

    private int cooldownTime = -1;
    private long tickedGameTime;
    public TrashCanBlockEntity(final BlockPos position, final BlockState state) {
        super(ModBlockEntities.TRASH_CAN.get(), position, state);
    }

    public void load(@NotNull final CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);

    }

    protected void saveAdditional(@NotNull final CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(final int id, @NotNull final Inventory player) {
        return new TrashCanMenu(id, player, this, ContainerLevelAccess.create(Objects.requireNonNull(this.level), this.worldPosition));
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @NotNull
    @Override
    public ItemStack getItem(final int slot) {
        return this.getItems().get(slot);
    }

    @Override
    public void setItem(final int slot, @NotNull final ItemStack stack) {
        this.getItems().set(slot, stack);
    }

    @NotNull
    @Override
    public ItemStack removeItem(final int slot, final int amount) {
        final ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), slot, amount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @NotNull
    @Override
    public ItemStack removeItemNoUpdate(final int slot) {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    @Override
    public boolean stillValid(@NotNull final Player pPlayer) {
        assert null != this.level;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(64.0D < pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D));
        }
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.smallutils.trash_can");
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(final NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    public static boolean suckInItems(final Level level, final Hopper hopper) {
        final Container container = getSourceContainer(level, hopper);
        if (container != null) {
            final Direction direction = Direction.DOWN;
            if (isEmptyContainer(container, direction)) {
                return false;
            }
            return getSlots(container, direction).anyMatch(i -> tryTakeInItemFromSlot(hopper, container, i, direction));
        }
        for (final ItemEntity itemEntity : getItemsAtAndAbove(level, hopper)) {
            if (!addItem(hopper, itemEntity)) continue;
            return true;
        }
        return false;
    }

    private static boolean canTakeItemFromContainer(final Container container, final ItemStack itemStack, final int i, final Direction direction) {
        return !(container instanceof WorldlyContainer) || ((WorldlyContainer)container).canTakeItemThroughFace(i, itemStack, direction);
    }

    private static boolean tryTakeInItemFromSlot(final Hopper hopper, final Container container, final int i, final Direction direction) {
        final ItemStack itemStack = container.getItem(i);
        if (!itemStack.isEmpty() && canTakeItemFromContainer(container, itemStack, i, direction)) {
            final ItemStack itemStack2 = itemStack.copy();
            final ItemStack itemStack3 = addItem(container, hopper, container.removeItem(i, 1), null);
            if (itemStack3.isEmpty()) {
                container.setChanged();
                return true;
            }
            container.setItem(i, itemStack2);
        }
        return false;
    }

    public static List<ItemEntity> getItemsAtAndAbove(final Level level, final Hopper hopper) {
        return hopper.getSuckShape().toAabbs().stream().flatMap(aABB -> level.getEntitiesOfClass(ItemEntity.class, aABB.move(hopper.getLevelX() - 0.5, hopper.getLevelY() - 0.5, hopper.getLevelZ() - 0.5), EntitySelector.ENTITY_STILL_ALIVE).stream()).collect(Collectors.toList());
    }

    private static boolean isEmptyContainer(final Container container, final Direction direction) {
        return getSlots(container, direction).allMatch(i -> container.getItem(i).isEmpty());
    }

    private static IntStream getSlots(final Container container, final Direction direction) {
        if (container instanceof WorldlyContainer) {
            return IntStream.of(((WorldlyContainer)container).getSlotsForFace(direction));
        }
        return IntStream.range(0, container.getContainerSize());
    }

    public static boolean addItem(final Container container, final ItemEntity itemEntity) {
        boolean bl = false;
        final ItemStack itemStack = itemEntity.getItem().copy();
        final ItemStack itemStack2 = addItem(null, container, itemStack, null);
        if (itemStack2.isEmpty()) {
            bl = true;
            itemEntity.discard();
        } else {
            itemEntity.setItem(itemStack2);
        }
        return bl;
    }

    private static ItemStack tryMoveInItem(@Nullable final Container container, final Container container2, ItemStack itemStack, final int i, @Nullable final Direction direction) {
        final ItemStack itemStack2 = container2.getItem(i);
        if (canPlaceItemInContainer(container2, itemStack, i, direction)) {
            int k;
            boolean bl = false;
            final boolean bl2 = container2.isEmpty();
            if (itemStack2.isEmpty()) {
                container2.setItem(i, itemStack);
                itemStack = ItemStack.EMPTY;
                bl = true;
            } else if (canMergeItems(itemStack2, itemStack)) {
                final int j = itemStack.getMaxStackSize() - itemStack2.getCount();
                k = Math.min(itemStack.getCount(), j);
                itemStack.shrink(k);
                itemStack2.grow(k);
                final boolean bl3 = bl = k > 0;
            }
            if (bl) {
                final TrashCanBlockEntity hopperBlockEntity;
                if (bl2 && container2 instanceof TrashCanBlockEntity && !(hopperBlockEntity = (TrashCanBlockEntity)container2).isOnCustomCooldown()) {
                    k = 0;
                    if (container instanceof final TrashCanBlockEntity hopperBlockEntity2) {
                        if (hopperBlockEntity.tickedGameTime >= hopperBlockEntity2.tickedGameTime) {
                            k = 1;
                        }
                    }
                    hopperBlockEntity.setCooldown(8 - k);
                }
                container2.setChanged();
            }
        }
        return itemStack;
    }

    private void setCooldown(final int i) {
        this.cooldownTime = i;
    }

    private boolean isOnCooldown() {
        return this.cooldownTime > 0;
    }

    private boolean isOnCustomCooldown() {
        return this.cooldownTime > 8;
    }

    private static boolean canMergeItems(final ItemStack itemStack, final ItemStack itemStack2) {
        if (!itemStack.is(itemStack2.getItem())) {
            return false;
        }
        if (itemStack.getDamageValue() != itemStack2.getDamageValue()) {
            return false;
        }
        if (itemStack.getCount() > itemStack.getMaxStackSize()) {
            return false;
        }
        return ItemStack.tagMatches(itemStack, itemStack2);
    }

    private static boolean canPlaceItemInContainer(final Container container, final ItemStack itemStack, final int i, @Nullable final Direction direction) {
        if (!container.canPlaceItem(i, itemStack)) {
            return false;
        }
        return !(container instanceof WorldlyContainer) || ((WorldlyContainer)container).canPlaceItemThroughFace(i, itemStack, direction);
    }

    public static ItemStack addItem(@Nullable final Container container, final Container container2, ItemStack itemStack, @Nullable final Direction direction) {
        if (container2 instanceof final WorldlyContainer worldlyContainer && direction != null) {
            final int[] is = worldlyContainer.getSlotsForFace(direction);
            for (int i = 0; i < is.length && !itemStack.isEmpty(); ++i) {
                itemStack = tryMoveInItem(container, container2, itemStack, is[i], direction);
            }
        } else {
            final int j = container2.getContainerSize();
            for (int k = 0; k < j && !itemStack.isEmpty(); ++k) {
                itemStack = tryMoveInItem(container, container2, itemStack, k, direction);
            }
        }
        return itemStack;
    }

    @Nullable
    private static Container getSourceContainer(final Level level, final Hopper hopper) {
        return getContainerAt(level, hopper.getLevelX(), hopper.getLevelY() + 1.0, hopper.getLevelZ());
    }

    @Nullable
    private static Container getContainerAt(final Level level, final double d, final double e, final double f) {
        final List<Entity> list;
        final BlockEntity blockEntity;
        Container container = null;
        final BlockPos blockPos = new BlockPos(d, e, f);
        final BlockState blockState = level.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block instanceof WorldlyContainerHolder) {
            container = ((WorldlyContainerHolder) block).getContainer(blockState, level, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = level.getBlockEntity(blockPos)) instanceof Container && (container = (Container) blockEntity) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            container = ChestBlock.getContainer((ChestBlock)block, blockState, level, blockPos, true);
        }
        if (container == null && !(list = level.getEntities((Entity)null, new AABB(d - 0.5, e - 0.5, f - 0.5, d + 0.5, e + 0.5, f + 0.5), EntitySelector.CONTAINER_ENTITY_SELECTOR)).isEmpty()) {
            container = (Container) list.get(level.random.nextInt(list.size()));
        }
        return container;
    }

//    @NotNull
//    @Override
//    public <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, final Direction side) {
//        if (!this.remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            if (null == this.chestHandler)
//                this.chestHandler = LazyOptional.of(this::createHandler);
//            return this.chestHandler.cast();
//        }
//        return super.getCapability(cap, side);
//    }
//
//    @NotNull
//    private IItemHandlerModifiable createHandler() {
//        final BlockState state = this.getBlockState();
//        if (!(state.getBlock() instanceof ChestBlock)) {
//            return new InvWrapper(this);
//        }
//        final Container inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), true);
//        return new InvWrapper(null == inv ? this : inv);
//    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockState(@NotNull final BlockState blockState) {
        super.setBlockState(blockState);
//        if (null != this.chestHandler) {
//            final LazyOptional<?> oldHandler = this.chestHandler;
//            this.chestHandler = null;
//            oldHandler.invalidate();
//        }
    }

//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        if (null != this.chestHandler) {
//            this.chestHandler.invalidate();
//            this.chestHandler = null;
//        }
//    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }

    public void trash() {
        if (this.isEmpty()) {
            return;
        }
        final int xp = this.calculateXp(this.level.getRandom());

        this.clearContent();

        if (null != this.level) {
            this.level.playSound(null, this.worldPosition, SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            SmallUtilities.LOGGER.error(MARKER, "Tried to play sound at {} but level is null", this.worldPosition);
        }

        this.level.addFreshEntity(new ExperienceOrb(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.5, this.worldPosition.getZ() + 0.5, xp));
    }

    public final int calculateXp(final Random random) {
        final int min = this.calculateMaxXp() / this.getXpReduction();
        final int max = this.calculateMaxXp();
        return random.nextInt(min, max);
    }

    public int getXpReduction() {
        return 4;
    }

    public final int calculateMinXp() {
        return this.calculateMaxXp() / this.getXpReduction();
    }

    public int calculateMaxXp() {
        int xp = 0;
        for (int i = 0; TrashCanMenu.SLOTS > i; ++i) {
            final ItemStack itemStack = this.items.get(i);
            if (!itemStack.isEmpty()) {
                xp += itemStack.getCount() * (1 + EnchantmentHelper.getEnchantments(itemStack).keySet().stream().filter(enchantment -> !enchantment.isCurse()).count() * 2);
            }
        }
        return xp;
    }

    @Override
    public void tick() {

    }
}
