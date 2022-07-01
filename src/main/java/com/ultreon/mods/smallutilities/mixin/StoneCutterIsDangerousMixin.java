package com.ultreon.mods.smallutilities.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StonecutterBlock;
import org.spongepowered.asm.mixin.Mixin;

@Deprecated
@Mixin(StonecutterBlock.class)
public abstract class StoneCutterIsDangerousMixin extends Block {
    public StoneCutterIsDangerousMixin(Properties p_49795_) {
        super(p_49795_);
    }
}
