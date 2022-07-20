package com.ultreon.mods.smallutilities.data;

import com.ultreon.mods.smallutilities.SmallUtilities;
import com.ultreon.mods.smallutilities.data.client.ModBlockStates;
import com.ultreon.mods.smallutilities.data.client.ModItemModels;
import com.ultreon.mods.smallutilities.data.lang.ModDutchLanguage;
import com.ultreon.mods.smallutilities.data.lang.ModEnglishLanguage;
import com.ultreon.mods.smallutilities.data.loot.ModLoot;
import com.ultreon.mods.smallutilities.data.recipes.ModRecipes;
import com.ultreon.mods.smallutilities.data.tags.ModBlockTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SmallUtilities.MOD_ID)
public class ModDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModBlockStates(generator, exFileHelper));
        generator.addProvider(new ModItemModels(generator, exFileHelper));
        generator.addProvider(new ModRecipes(generator));
        generator.addProvider(new ModLoot(generator));
        generator.addProvider(new ModBlockTags(generator, exFileHelper));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "us"));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "gb"));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "au"));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "ca"));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "nz"));
        generator.addProvider(ModEnglishLanguage.ofCountry(generator, exFileHelper, "npy"));
        generator.addProvider(ModDutchLanguage.ofCountry(generator, exFileHelper, "nl"));
        generator.addProvider(ModDutchLanguage.ofCountry(generator, exFileHelper, "be"));
        generator.addProvider(ModDutchLanguage.ofCountry(generator, exFileHelper, "an"));
    }
}
