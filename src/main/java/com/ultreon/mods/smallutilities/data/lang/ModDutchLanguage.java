package com.ultreon.mods.smallutilities.data.lang;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.FutureDashboardBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import com.ultreon.mods.smallutilities.init.ModDamageSources;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

public class ModDutchLanguage extends LanguageProvider {
    public ModDutchLanguage(DataGenerator generator, ExistingFileHelper exFileHelper, String country) {
        super(generator, SmallUtilities.MOD_ID, "nl_" + country);
    }

    public static ModDutchLanguage ofCountry(DataGenerator generator, ExistingFileHelper exFileHelper, String country) {
        return new ModDutchLanguage(generator, exFileHelper, country);
    }

    @Override
    protected void addTranslations() {
        // Generic decorative blocks and stations.
        addBlock(ModBlocks.LAPTOP, "Laptop");
        addBlock(ModBlocks.IRON_GRID_PLATE, "IJzeren rasterplaat");
        addBlock(ModBlocks.TRASH_CAN, "Vuilnisbak");

        addDamageSource(ModDamageSources.SLICED_BY_STONE_CUTTER, "%1$s werd gesneden door een steenzaag");
        addDamageSource(ModDamageSources.TRASHED_THEMSELF, "%1$s heeft zichzelf verwijderd");

        //***************************//
        //     Wood types blocks     //
        //***************************//

        // Coffee Tables
        addBlock(ModBlocks.OAK_COFFEE_TABLE, "Eiken koffietafel");
        addBlock(ModBlocks.BIRCH_COFFEE_TABLE, "Berken koffietafel");
        addBlock(ModBlocks.SPRUCE_COFFEE_TABLE, "Sparren koffietafel");
        addBlock(ModBlocks.JUNGLE_COFFEE_TABLE, "Oerwoudhouten koffietafel");
        addBlock(ModBlocks.ACACIA_COFFEE_TABLE, "Acacia koffietafel");
        addBlock(ModBlocks.DARK_OAK_COFFEE_TABLE, "Donkere eiken koffietafel");

        // Tables
        addBlock(ModBlocks.OAK_TABLE, "Eiken tafel");
        addBlock(ModBlocks.BIRCH_TABLE, "Berken tafel");
        addBlock(ModBlocks.SPRUCE_TABLE, "Sparren tafel");
        addBlock(ModBlocks.JUNGLE_TABLE, "Oerwoudhouten tafel");
        addBlock(ModBlocks.ACACIA_TABLE, "Acacia tafel");
        addBlock(ModBlocks.DARK_OAK_TABLE, "Donkere eiken tafel");

        for (FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            addBlock(() -> block, "Future dashboard");
        }

        addBlock(ModBlocks.FUTURE_DASHBOARD, "Future dashboard");
    }

    private void addDamageSource(DamageSource key, String name) {
        add("death.attack." + key.msgId, name);
        add("death.attack." + key.msgId + ".player", name + " om %2$s te ontlopen");
    }
}
