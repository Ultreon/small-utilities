package com.ultreon.mods.smallutilities.data.recipes;

import com.ultreon.mods.smallutilities.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(final DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(final Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.TRASH_CAN.get())
                .pattern("/#/")
                .pattern("/O/")
                .pattern("///")
                .define('#', Tags.Items.NUGGETS_IRON)
                .define('/', Tags.Items.INGOTS_IRON)
                .define('O', Items.PAPER)
                .unlockedBy("has_iron_ingot", RecipeProvider.has(Tags.Items.INGOTS_IRON))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.LAPTOP.get())
                .pattern("###")
                .pattern("#O#")
                .pattern("///")
                .define('#', Tags.Items.NUGGETS_IRON)
                .define('/', Tags.Items.INGOTS_IRON)
                .define('O', Tags.Items.GLASS_PANES)
                .unlockedBy("has_iron_ingot", RecipeProvider.has(Tags.Items.INGOTS_IRON))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.IRON_GRID_PLATE.get())
                .pattern("###")
                .pattern("###")
                .define('#', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_iron_nugget", RecipeProvider.has(Tags.Items.NUGGETS_IRON))
                .save(consumer);

        // Coffee tables
        ShapedRecipeBuilder.shaped(ModBlocks.OAK_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_OAK_LOG, Items.STRIPPED_OAK_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_OAK_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_OAK_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BIRCH_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_BIRCH_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_BIRCH_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.SPRUCE_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_SPRUCE_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_SPRUCE_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.JUNGLE_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_JUNGLE_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_JUNGLE_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.ACACIA_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_ACACIA_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_ACACIA_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.DARK_OAK_COFFEE_TABLE.get(), 6)
                .pattern("###")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_DARK_OAK_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_DARK_OAK_WOOD))
                .save(consumer);

        // Normal table
        ShapedRecipeBuilder.shaped(ModBlocks.OAK_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_OAK_LOG, Items.STRIPPED_OAK_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_OAK_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_OAK_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BIRCH_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_BIRCH_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_BIRCH_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.SPRUCE_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_SPRUCE_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_SPRUCE_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.JUNGLE_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_JUNGLE_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_JUNGLE_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.ACACIA_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_ACACIA_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_ACACIA_WOOD))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.DARK_OAK_TABLE.get(), 4)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', Ingredient.of(Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD))
                .unlockedBy("has_stripped_log", RecipeProvider.has(Items.STRIPPED_DARK_OAK_LOG))
                .unlockedBy("has_stripped_wood", RecipeProvider.has(Items.STRIPPED_DARK_OAK_WOOD))
                .save(consumer);
    }
}
