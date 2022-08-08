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
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("unused")
public class TrashCanBlockEntity extends BaseContainerBlockEntity implements TickableBlockEntity {
    private static final Marker MARKER = MarkerFactory.getMarker("TRASH-CAN:BE");
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private LazyOptional<IItemHandlerModifiable> chestHandler;

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

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, final Direction side) {
        if (!this.remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (null == this.chestHandler)
                this.chestHandler = LazyOptional.of(this::createHandler);
            return this.chestHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @NotNull
    private IItemHandlerModifiable createHandler() {
        final BlockState state = this.getBlockState();
        if (!(state.getBlock() instanceof ChestBlock)) {
            return new InvWrapper(this);
        }
        final Container inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), true);
        return new InvWrapper(null == inv ? this : inv);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockState(@NotNull final BlockState blockState) {
        super.setBlockState(blockState);
        if (null != this.chestHandler) {
            final LazyOptional<?> oldHandler = this.chestHandler;
            this.chestHandler = null;
            oldHandler.invalidate();
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (null != this.chestHandler) {
            this.chestHandler.invalidate();
            this.chestHandler = null;
        }
    }

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
