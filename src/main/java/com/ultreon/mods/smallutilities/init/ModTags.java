package com.ultreon.mods.smallutilities.init;

import com.ultreon.mods.smallutilities.SmallUtilities;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static void init() {
        EntityTypes.init();
    }

    public static class EntityTypes {
//        public static final TagKey<EntityType<?>> BUG = tag("bug");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }

    public static class Blocks {

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }

    public static class Items {
        public static final TagKey<Item> TRASH_BLACKLIST = tag("trash_blacklist");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(SmallUtilities.res(name));
        }

        public static void init() {
            // NO-OP
        }
    }
}
