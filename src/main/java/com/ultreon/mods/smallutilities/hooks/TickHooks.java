package com.ultreon.mods.smallutilities.hooks;

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
    public static final int STONE_CUTTER_TICK_DELAY = 10;

    private static int stoneCutterDamageTicker = 0;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level;
        BlockState blockState = level.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            if (blockState.getBlock() instanceof StonecutterBlock) {
                if (++stoneCutterDamageTicker >= STONE_CUTTER_TICK_DELAY) {
                    stoneCutterDamageTicker = 0;
                    player.hurt(ModDamageSources.SLICED_BY_STONE_CUTTER, 3.0F);
                }
            } else if (stoneCutterDamageTicker != STONE_CUTTER_TICK_DELAY) {
                stoneCutterDamageTicker = STONE_CUTTER_TICK_DELAY;
            }
        }
    }
}
