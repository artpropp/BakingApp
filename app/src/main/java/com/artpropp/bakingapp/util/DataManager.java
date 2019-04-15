package com.artpropp.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.artpropp.bakingapp.model.Recipe;

import java.util.List;

public class DataManager {

    private static final String SELECTED_RECIPE_ID = "selected-recipe-index";

    public static void setWidgetRecipeId(Context context, int recipeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putInt(SELECTED_RECIPE_ID, recipeId)
                .apply();
    }

    public static Recipe getWidgetRecipe(Context context) {
        List<Recipe> recipes = RecipeReaderUtil.getRecipes(context);
        if (recipes == null || recipes.isEmpty()) return null;

        int defaultId = recipes.get(0).getId();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int selectedRecipeId = sharedPreferences.getInt(SELECTED_RECIPE_ID, defaultId);

        for (Recipe recipe : recipes) {
            if (recipe.getId() == selectedRecipeId) {
                return recipe;
            }
        }

        return recipes.get(0);
    }

}
