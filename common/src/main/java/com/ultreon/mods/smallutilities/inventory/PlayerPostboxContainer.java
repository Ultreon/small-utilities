//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ultreon.mods.smallutilities.inventory;

import com.ultreon.mods.smallutilities.block.entity.PostboxBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPostboxContainer extends SimpleContainer {
    @Nullable
    private PostboxBlockEntity activePostbox;

    public PlayerPostboxContainer() {
        super(27);
    }

    public void setActivePostbox(@Nullable PostboxBlockEntity blockEntity) {
        this.activePostbox = blockEntity;
    }

    public boolean isActivePostbox(@Nullable PostboxBlockEntity blockEntity) {
        return this.activePostbox == blockEntity;
    }

    public void fromTag(@NotNull ListTag listTag) {
        int i;
        for(i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for(i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255;
            if (j < this.getContainerSize()) {
                this.setItem(j, ItemStack.of(compoundTag));
            }
        }

    }

    public @NotNull ListTag createTag() {
        ListTag listTag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)i);
                itemStack.save(compoundTag);
                listTag.add(compoundTag);
            }
        }

        return listTag;
    }

    public boolean stillValid(@NotNull Player player) {
        return (this.activePostbox == null || this.activePostbox.stillValid(player)) && super.stillValid(player);
    }

    public void startOpen(@NotNull Player player) {
        if (this.activePostbox != null) {
            this.activePostbox.startOpen(player);
        }

        super.startOpen(player);
    }

    public void stopOpen(@NotNull Player player) {
        if (this.activePostbox != null) {
            this.activePostbox.stopOpen(player);
        }

        super.stopOpen(player);
        this.activePostbox = null;
    }
}
