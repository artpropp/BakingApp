package com.artpropp.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artpropp.bakingapp.model.Ingredient;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.DataManager;

import java.util.List;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> mIngredients;

    IngredientsRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Recipe recipe = DataManager.getWidgetRecipe(mContext);
        if (recipe == null) return;
        mIngredients = recipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mIngredients.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);

        views.setTextViewText(R.id.widget_quantity_measure_text_view, ingredient.getQuantityAndMeasure());
        views.setTextViewText(R.id.widget_ingredient_text_view, ingredient.getIngredient());

        Recipe recipe = DataManager.getWidgetRecipe(mContext);
        Intent fillInIntent = new Intent();
        fillInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        fillInIntent.putExtra(ItemListActivity.RECIPE_EXTRA, recipe);
        views.setOnClickFillInIntent(R.id.widget_ingredient_linear_layout, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
