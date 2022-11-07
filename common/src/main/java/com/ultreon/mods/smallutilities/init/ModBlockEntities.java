package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.entity.TrashCanBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("ConstantConditions")
public class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(SmallUtilities.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<TrashCanBlockEntity>> TRASH_CAN = REGISTER.register("trash_can", () -> BlockEntityType.Builder.of(TrashCanBlockEntity::new, ModBlocks.TRASH_CAN.get()).build(null));

    public static void register() {
        REGISTER.register();
    }
}
