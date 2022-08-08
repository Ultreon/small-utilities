package com.ultreon.mods.smallutilities.inventory.menu;

import com.ultreon.mods.smallutilities.init.ModBlockEntities;
import com.ultreon.mods.smallutilities.init.ModMenus;
import com.ultreon.mods.smallutilities.init.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TrashCanMenu extends AbstractContainerMenu {
    public static final int SLOTS = 5;
    private final Container container;

    private final ContainerLevelAccess access;

    public TrashCanMenu(final int id, final Inventory inventory) {
        this(id, inventory, new SimpleContainer(SLOTS), ContainerLevelAccess.NULL);
    }

    public TrashCanMenu(final int id, final Inventory inventory, final Container container, final ContainerLevelAccess access) {
        super(ModMenus.TRASH_CAN.get(), id);
        this.access = access;
        AbstractContainerMenu.checkContainerSize(container, SLOTS);
        this.container = container;
        container.startOpen(inventory.player);

        for (int k = 0; SLOTS > k; ++k) {
            addSlot(new Slot(container, k, 8 + k * 18, 18) {
                @Override
                public boolean mayPlace(final ItemStack stack) {
                    return !stack.is(ModTags.Items.TRASH_BLACKLIST);
                }
            });
        }

        for (int l = 0; 3 > l; ++l) {
            for (int j1 = 0; 9 > j1; ++j1) {
                addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 - 52));
            }
        }

        for (int i1 = 0; 9 > i1; ++i1) {
            addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 - 52));
        }
    }

    @Override
    public boolean clickMenuButton(@NotNull final Player player, final int id) {
        if (0 == id) {
            trash();
            return true;
        }
        return false;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @NotNull
    public ItemStack quickMoveStack(@NotNull final Player player, final int index) {
        ItemStack remainder = ItemStack.EMPTY;
        final Slot output = slots.get(index);
        if (output.hasItem()) {
            final ItemStack dest = output.getItem();
            remainder = dest.copy();
            if (5 > index) {
                if (!moveItemStackTo(dest, 5, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(dest, 0, 5, false)) {
                return ItemStack.EMPTY;
            }

            if (dest.isEmpty()) {
                output.set(ItemStack.EMPTY);
            } else {
                output.setChanged();
            }
        }

        return remainder;
    }

    @Override
    public boolean stillValid(@NotNull final Player player) {
        return container.stillValid(player);
    }

    /**
     * Called when the container is closed.
     */
    public void removed(@NotNull final Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    /**
     * Gets the inventory associated with this chest container.
     *
     * @see #container
     */
    public Container getContainer() {
        return container;
    }


    public void trash() {
        access.execute((level, blockPos) -> {
            level.getBlockEntity(blockPos, ModBlockEntities.TRASH_CAN.get()).ifPresent(trashCan -> {
                trashCan.trash();
//                for (int i = 0; i < SLOTS; i++) {
//                    ItemStack stack = this.container.getSlot(i).getItem();
//                    if (!stack.isEmpty()) {
//                        this.slots.get(i).set(ItemStack.EMPTY);
//                    }
//                }
            });
        });
    }

    public final int calculateXp(final Random random) {
        final int min = calculateMaxXp() / getXpReduction();
        final int max = calculateMaxXp();
        return random.nextInt(min, max);
    }

    public int getXpReduction() {
        return 4;
    }

    public final int calculateMinXp() {
        return calculateMaxXp() / getXpReduction();
    }

    public int calculateMaxXp() {
        int xp = 0;
        for (int i = 0; SLOTS > i; ++i) {
            final ItemStack itemStack = slots.get(i).getItem();
            if (!itemStack.isEmpty()) {
                xp += itemStack.getCount() * (1 + EnchantmentHelper.getEnchantments(itemStack).keySet().stream().filter(enchantment -> !enchantment.isCurse()).count() * 2);
            }
        }
        return xp;
    }

    public int getTrashItemCount() {
        int count = 0;
        for (int i = 0; SLOTS > i; ++i) {
            final ItemStack itemStack = slots.get(i).getItem();
            if (!itemStack.isEmpty()) {
                count += itemStack.getCount();
            }
        }
        return count;
    }
}
