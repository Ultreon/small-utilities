package com.ultreon.mods.smallutilities.mixin;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.inventory.PlayerPostboxContainer;
import com.ultreon.mods.smallutilities.post.PostboxManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "save", at = @At("HEAD"))
    public void smallUtils$save(ServerPlayer player, CallbackInfo ci) {
        postbox: {
            PlayerPostboxContainer container = PostboxManager.INSTANCE.get(player, false);
            if (container == null) {
                break postbox;
            }

            try {
                PostboxManager.INSTANCE.save(player, container);
            } catch (IOException e) {
                SmallUtilities.LOGGER.warn("Failed to save postbox data for player {}", player.getName());
            }
        }
    }
}
