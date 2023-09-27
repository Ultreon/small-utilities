package com.ultreon.mods.smallutilities.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class Utils {
    public static float calcMaxXp(List<ItemStack> items) {
        float xp = 0;
        for (final ItemStack itemStack : items) {
            var localXp = 0;
            if (!itemStack.isEmpty()) {
                localXp += itemStack.getCount() * (1 + EnchantmentHelper.getEnchantments(itemStack).keySet().stream().filter(enchantment -> !enchantment.isCurse()).count() * 2);

                switch (itemStack.getRarity()) {
                    case UNCOMMON -> localXp *= 1.325F;
                    case RARE -> localXp *= 1.75F;
                    case EPIC -> localXp *= 2.5F;
                }
            }
            xp += localXp;
        }
        return xp;
    }
}
