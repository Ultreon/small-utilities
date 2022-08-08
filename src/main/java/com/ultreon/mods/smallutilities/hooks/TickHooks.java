package com.ultreon.mods.smallutilities.hooks;

import com.ultreon.mods.smallutilities.Config;
import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.init.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SmallUtilities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickHooks {
    private static int stoneCutterDamageTicker;

    @SubscribeEvent
    public static void playerTick(final TickEvent.PlayerTickEvent event) {
        final int stoneCutterDamageDelay = Config.STONE_CUTTER_DAMAGE_DELAY.get();

        final Player player = event.player;
        final Level level = player.level;
        final BlockState blockState = level.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        if (TickEvent.Phase.START == event.phase && LogicalSide.SERVER == event.side) {
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
