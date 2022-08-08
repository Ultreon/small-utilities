package com.ultreon.mods.smallutilities.data.lang;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.FutureDashboardBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import com.ultreon.mods.smallutilities.init.ModDamageSources;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnglishLanguage extends LanguageProvider {
    public ModEnglishLanguage(final DataGenerator generator, final ExistingFileHelper exFileHelper, final String country) {
        super(generator, SmallUtilities.MOD_ID, "en_" + country);
    }

    public static ModEnglishLanguage ofCountry(final DataGenerator generator, final ExistingFileHelper exFileHelper, final String country) {
        return new ModEnglishLanguage(generator, exFileHelper, country);
    }

    @Override
    protected void addTranslations() {
        // Generic decorative blocks and stations.
        addBlock(ModBlocks.LAPTOP, "Laptop");
        addBlock(ModBlocks.IRON_GRID_PLATE, "Iron Grid Plate");
        addBlock(ModBlocks.TRASH_CAN, "Trash Can");

        addDamageSource(ModDamageSources.SLICED_BY_STONE_CUTTER, "%1$s was sliced by a stone cutter");
        addDamageSource(ModDamageSources.TRASHED_THEMSELF, "%1$s has trashed themself");

        //***************************//
        //     Wood types blocks     //
        //***************************//

        // Coffee Tables
        addBlock(ModBlocks.OAK_COFFEE_TABLE, "Oak Coffee Table");
        addBlock(ModBlocks.BIRCH_COFFEE_TABLE, "Birch Coffee Table");
        addBlock(ModBlocks.SPRUCE_COFFEE_TABLE, "Spruce Coffee Table");
        addBlock(ModBlocks.JUNGLE_COFFEE_TABLE, "Jungle Coffee Table");
        addBlock(ModBlocks.ACACIA_COFFEE_TABLE, "Acacia Coffee Table");
        addBlock(ModBlocks.DARK_OAK_COFFEE_TABLE, "Dark Oak Coffee Table");

        // Tables
        addBlock(ModBlocks.OAK_TABLE, "Oak Table");
        addBlock(ModBlocks.BIRCH_TABLE, "Birch Table");
        addBlock(ModBlocks.SPRUCE_TABLE, "Spruce Table");
        addBlock(ModBlocks.JUNGLE_TABLE, "Jungle Table");
        addBlock(ModBlocks.ACACIA_TABLE, "Acacia Table");
        addBlock(ModBlocks.DARK_OAK_TABLE, "Dark Oak Table");

        for (final FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            addBlock(() -> block, "Future Dashboard");
        }

        addBlock(ModBlocks.FUTURE_DASHBOARD, "Future Dashboard");

        add("container.smallutils.trash_can", "Trash Can");
        add("container.smallutils.trash_can.trash", "Trash");
        add("container.smallutils.trash_can.delete_confirmation.title", "Delete Confirmation");
        add("container.smallutils.trash_can.delete_confirmation.description", "You're deleting %d items, are you sure you want to delete them?");
        add("container.smallutils.trash_can.delete_confirmation.description1", "This will grant you between %d and %d XP.");
    }

    private void addDamageSource(final DamageSource key, final String name) {
        add("death.attack." + key.msgId, name);
        add("death.attack." + key.msgId + ".player", name + " whilst fighting %2$s");
    }
}
