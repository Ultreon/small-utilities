package com.ultreon.mods.smallutilities.forge.data.lang;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.block.FutureDashboardBlock;
import com.ultreon.mods.smallutilities.init.ModBlocks;
import com.ultreon.mods.smallutilities.init.ModDamageSources;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

public class ModDutchLanguage extends LanguageProvider {
    public ModDutchLanguage(final DataGenerator generator, final ExistingFileHelper exFileHelper, final String country) {
        super(generator, SmallUtilities.MOD_ID, "nl_" + country);
    }

    public static ModDutchLanguage ofCountry(final DataGenerator generator, final ExistingFileHelper exFileHelper, final String country) {
        return new ModDutchLanguage(generator, exFileHelper, country);
    }

    @Override
    protected void addTranslations() {
        // Generic decorative blocks and stations.
        this.addBlock(ModBlocks.LAPTOP, "Laptop");
        this.addBlock(ModBlocks.IRON_GRID_PLATE, "IJzeren rasterplaat");
        this.addBlock(ModBlocks.TRASH_CAN, "Vuilnisbak");

        this.addDamageSource(ModDamageSources.SLICED_BY_STONE_CUTTER, "%1$s werd gesneden door een steenzaag");
        this.addDamageSource(ModDamageSources.TRASHED_THEMSELF, "%1$s heeft zichzelf verwijderd");

        //***************************//
        //     Wood types blocks     //
        //***************************//

        // Coffee Tables
        this.addBlock(ModBlocks.OAK_COFFEE_TABLE, "Eiken koffietafel");
        this.addBlock(ModBlocks.BIRCH_COFFEE_TABLE, "Berken koffietafel");
        this.addBlock(ModBlocks.SPRUCE_COFFEE_TABLE, "Sparren koffietafel");
        this.addBlock(ModBlocks.JUNGLE_COFFEE_TABLE, "Oerwoudhouten koffietafel");
        this.addBlock(ModBlocks.ACACIA_COFFEE_TABLE, "Acacia koffietafel");
        this.addBlock(ModBlocks.DARK_OAK_COFFEE_TABLE, "Donkere eiken koffietafel");

        // Tables
        this.addBlock(ModBlocks.OAK_TABLE, "Eiken tafel");
        this.addBlock(ModBlocks.BIRCH_TABLE, "Berken tafel");
        this.addBlock(ModBlocks.SPRUCE_TABLE, "Sparren tafel");
        this.addBlock(ModBlocks.JUNGLE_TABLE, "Oerwoudhouten tafel");
        this.addBlock(ModBlocks.ACACIA_TABLE, "Acacia tafel");
        this.addBlock(ModBlocks.DARK_OAK_TABLE, "Donkere eiken tafel");

        for (final FutureDashboardBlock block : ModBlocks.getAllFutureDashboards().toList()) {
            this.addBlock(() -> block, "Futuristische dashboard");
        }

        this.addBlock(ModBlocks.FUTURE_DASHBOARD, "Futuristische dashboard");

        this.add("container.smallutils.trash_can", "Vuilnisbak");
        this.add("container.smallutils.trash_can.trash", "Verwijder");
        this.add("container.smallutils.trash_can.delete_confirmation.title", "Verwijderings-bevestiging");
        this.add("container.smallutils.trash_can.delete_confirmation.description", "Je probeert %d items te verwijderen, weet je zeker dat je dat wilt?");
        this.add("container.smallutils.trash_can.delete_confirmation.description1", "Dit gaat je tussen %d en %d XP opleveren.");
    }

    private void addDamageSource(final DamageSource key, final String name) {
        this.add("death.attack." + key.msgId, name);
        this.add("death.attack." + key.msgId + ".player", name + " om %2$s te ontlopen");
    }
}
