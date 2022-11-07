package com.ultreon.mods.smallutilities.forge.data.lang;

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
        this.addBlock(ModBlocks.LAPTOP, "Laptop");
        this.addBlock(ModBlocks.IRON_GRID_PLATE, "Iron Grid Plate");
        this.addBlock(ModBlocks.TRASH_CAN, "Trash Can");

        this.addDamageSource(ModDamageSources.SLICED_BY_STONE_CUTTER, "%1$s was sliced by a stone cutter");
        this.addDamageSource(ModDamageSources.TRASHED_THEMSELF, "%1$s has trashed themself");

        //***************************//
        //     Wood types blocks     //
        //***************************//

        // Coffee Tables
        this.addBlock(ModBlocks.OAK_COFFEE_TABLE, "Oak Coffee Table");
        this.addBlock(ModBlocks.BIRCH_COFFEE_TABLE, "Birch Coffee Table");
        this.addBlock(ModBlocks.SPRUCE_COFFEE_TABLE, "Spruce Coffee Table");
        this.addBlock(ModBlocks.JUNGLE_COFFEE_TABLE, "Jungle Coffee Table");
        this.addBlock(ModBlocks.ACACIA_COFFEE_TABLE, "Acacia Coffee Table");
        this.addBlock(ModBlocks.DARK_OAK_COFFEE_TABLE, "Dark Oak Coffee Table");

        // Tables
        this.addBlock(ModBlocks.OAK_TABLE, "Oak Table");
        this.addBlock(ModBlocks.BIRCH_TABLE, "Birch Table");
        this.addBlock(ModBlocks.SPRUCE_TABLE, "Spruce Table");
        this.addBlock(ModBlocks.JUNGLE_TABLE, "Jungle Table");
        this.addBlock(ModBlocks.ACACIA_TABLE, "Acacia Table");
        this.addBlock(ModBlocks.DARK_OAK_TABLE, "Dark Oak Table");

        for (final FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            this.addBlock(() -> block, "Future Dashboard");
        }

        this.addBlock(ModBlocks.FUTURE_DASHBOARD, "Future Dashboard");

        this.add("container.smallutils.trash_can", "Trash Can");
        this.add("container.smallutils.trash_can.trash", "Trash");
        this.add("container.smallutils.trash_can.delete_confirmation.title", "Delete Confirmation");
        this.add("container.smallutils.trash_can.delete_confirmation.description", "You're deleting %d items, are you sure you want to delete them?");
        this.add("container.smallutils.trash_can.delete_confirmation.description1", "This will grant you between %d and %d XP.");
    }

    private void addDamageSource(final DamageSource key, final String name) {
        this.add("death.attack." + key.msgId, name);
        this.add("death.attack." + key.msgId + ".player", name + " whilst fighting %2$s");
    }
}
