package com.chromanyan.meaningfulmaterials.datagen;

import com.chromanyan.meaningfulmaterials.MeaningfulMaterials;
import com.chromanyan.meaningfulmaterials.init.MMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MMRecipes extends RecipeProvider {

    public MMRecipes(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @SuppressWarnings("SameParameterValue")
    private void packAndUnpack(@NotNull Consumer<FinishedRecipe> consumer, ItemLike unpacked, ItemLike packed, String name, String name2) {
        ShapelessRecipeBuilder.shapeless(unpacked, 9)
                .requires(packed)
                .unlockedBy("has_unpackable", has(unpacked))
                .save(consumer, new ResourceLocation(MeaningfulMaterials.MODID, name + "_unpack"));
        ShapedRecipeBuilder.shaped(packed, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', unpacked)
                .unlockedBy("has_unpackable", has(unpacked))
                .save(consumer, new ResourceLocation(MeaningfulMaterials.MODID, name2));
    }

    @SuppressWarnings("SameParameterValue")
    private void oreProcessing(@NotNull Consumer<FinishedRecipe> consumer, ItemLike ore, ItemLike result, String name, float xp, int time) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ore), result, xp, time)
                .unlockedBy("has_ore", has(ore))
                .save(consumer, new ResourceLocation(MeaningfulMaterials.MODID, name + "_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ore), result, xp, time / 2)
                .unlockedBy("has_ore", has(ore))
                .save(consumer, new ResourceLocation(MeaningfulMaterials.MODID, name + "_blasting"));
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        packAndUnpack(consumer, MMItems.COSMITE.get(), MMItems.COSMITE_BLOCK_ITEM.get(), "cosmite", "cosmite_block");

        oreProcessing(consumer, MMItems.COSMITE_ORE_ITEM.get(), MMItems.COSMITE.get(), "cosmite", 1.5f, 200);
    }
}