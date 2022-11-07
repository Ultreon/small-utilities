package com.ultreon.mods.smallutilities.hooks;

import com.ultreon.mods.smallutilities.Config;
import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.init.ModDamageSources;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TickHooks {
    private static int stoneCutterDamageTicker;

    public static void init() {
        TickEvent.PLAYER_PRE.register(TickHooks::playerTick);
    }

    public static void playerTick(final Player player) {
        final int stoneCutterDamageDelay = Config.STONE_CUTTER_DAMAGE_DELAY.get();

        final Level level = player.level;
        final BlockState blockState = level.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        if (!level.isClientSide) {
            if (blockState.getBlock() instanceof StonecutterBlock) {
                if (++stoneCutterDamageTicker >= stoneCutterDamageDelay) {
                    stoneCutterDamageTicker = 0;
                    player.hurt(ModDamageSources.SLICED_BY_STONE_CUTTER, Config.STONE_CUTTER_DAMAGE.get().floatValue());
                }
            } else if (stoneCutterDamageTicker != stoneCutterDamageDelay) {
                stoneCutterDamageTicker = stoneCutterDamageDelay;
            }
        }
    }
}
