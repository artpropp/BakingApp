package com.artpropp.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.artpropp.bakingapp.api.RecipeApi;
import com.artpropp.bakingapp.model.Recipe;

import io.reactivex.Observable;

public class DataManager {

    private DataManager() { /* hide constructor for this static helper class */ }

    private static final String SELECTED_RECIPE_ID = "selected-recipe-index";

    public static void setWidgetRecipeId(Context context, int recipeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putInt(SELECTED_RECIPE_ID, recipeId)
                .apply();
    }

    public static Observable<Recipe> getWidgetRecipe(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int selectedRecipeId = sharedPreferences.getInt(SELECTED_RECIPE_ID, 0);

        return RecipeApi.getRecipes()
                .map( recipes -> {
                    for (Recipe recipe: recipes) {
                        if (recipe.getId() == selectedRecipeId) {
                            return recipe;
                        }
                    }
                    return recipes.get(0);
                } );
    }

}
