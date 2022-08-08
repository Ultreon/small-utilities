package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.entity.TrashCanBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SmallUtilities.MOD_ID);

    public static final RegistryObject<BlockEntityType<TrashCanBlockEntity>> TRASH_CAN = REGISTER.register("trash_can", () -> BlockEntityType.Builder.of(TrashCanBlockEntity::new, ModBlocks.TRASH_CAN.get()).build(null));

    public static void register(final IEventBus modEvents) {
        REGISTER.register(modEvents);
    }
}
