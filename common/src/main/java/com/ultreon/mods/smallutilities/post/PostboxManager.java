package com.ultreon.mods.smallutilities.post;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.inventory.PlayerPostboxContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostboxManager {
    public static final PostboxManager INSTANCE = new PostboxManager();
    private final Map<UUID, PlayerPostboxContainer> containers = new HashMap<>();

    public PlayerPostboxContainer get(Player player) {
        return get(player, true);
    }

    public PlayerPostboxContainer get(Player player, boolean load) {
        var container = containers.get(player.getUUID());

        if (container == null) {
            if (!load) {
                return null;
            }

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return new PlayerPostboxContainer();
            }
            try {
                container = load(serverPlayer);
            } catch (IOException e) {
                return null;
            }
            containers.put(player.getUUID(), container);
        }

        return container;
    }

    private PlayerPostboxContainer load(ServerPlayer serverPlayer) throws IOException {
        PlayerPostboxContainer container = new PlayerPostboxContainer();
        container.fromTag(SmallUtilities.readPlayerData(serverPlayer, "postbox.dat").getList("Items", Tag.TAG_COMPOUND));
        return container;
    }

    public void save(ServerPlayer serverPlayer, PlayerPostboxContainer container) throws IOException {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("Items", container.createTag());
        SmallUtilities.writePlayerData(serverPlayer, "postbox.dat", compoundTag);
    }
}
