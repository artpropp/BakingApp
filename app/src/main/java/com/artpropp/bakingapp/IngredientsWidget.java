package com.artpropp.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.artpropp.bakingapp.util.DataManager;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private static final String TAG = "INGREDIENTS_WIDGET";

    private static CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);

        mCompositeDisposable.clear();
        mCompositeDisposable.add(
                DataManager.getWidgetRecipe(context).subscribe(
                        recipe -> {
                            views.setTextViewText(R.id.widget_text_view, recipe.getName());

                            Intent appIntent = new Intent(context, ItemListActivity.class);
                            PendingIntent appPendingIntent = PendingIntent.getActivity(
                                    context, 0, appIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);

                            appWidgetManager.updateAppWidget(appWidgetId, views);
                        },
                        throwable -> Log.e(TAG, throwable.getMessage())
                )
        );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        mCompositeDisposable.clear();
    }

}

