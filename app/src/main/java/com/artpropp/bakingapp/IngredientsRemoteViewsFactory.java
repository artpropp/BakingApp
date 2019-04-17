package com.artpropp.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artpropp.bakingapp.model.Ingredient;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.DataManager;

import io.reactivex.disposables.CompositeDisposable;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "INGREDIENTS_FACTORY";

    private Context mContext;
    private Recipe mRecipe;
    private CompositeDisposable mCompositeDisposable;

    IngredientsRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDataSetChanged() {
        mCompositeDisposable.add(
                DataManager.getWidgetRecipe(mContext)
                        .subscribe(
                                recipe -> {
                                    if (recipe == null) return;
                                    mRecipe = recipe;
                                },
                                throwable -> Log.e(TAG, throwable.getMessage())
                        )
        );
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    @Override
    public int getCount() {
        if (mRecipe == null || mRecipe.getIngredients() == null) return 0;
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mRecipe.getIngredients().get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);

        views.setTextViewText(R.id.widget_quantity_measure_text_view, ingredient.getQuantityAndMeasure());
        views.setTextViewText(R.id.widget_ingredient_text_view, ingredient.getIngredient());

        Intent fillInIntent = new Intent();
        fillInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        fillInIntent.putExtra(ItemListActivity.RECIPE_EXTRA, mRecipe);
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
