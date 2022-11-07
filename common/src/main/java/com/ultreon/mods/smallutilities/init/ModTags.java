package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static void init() {
        EntityTypes.init();
    }

    public static class EntityTypes {
        private static TagKey<EntityType<?>> tag(final String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }

    public static class Blocks {
        private static TagKey<Block> tag(final String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }

    public static class Items {
        public static final TagKey<Item> TRASH_BLACKLIST = tag("trash_blacklist");

        private static TagKey<Item> tag(final String name) {
            return TagKey.create(Registry.ITEM_REGISTRY, SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }
}
