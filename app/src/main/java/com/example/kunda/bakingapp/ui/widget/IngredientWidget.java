package com.example.kunda.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = getIngredientServiceIntent(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setRemoteAdapter(R.id.ingredients_lv, intent);

        //Set Name of recipe , get name of saved recipe using Shared Preferences
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        String recipeName = appSharedPrefs.getString(StepListActivity.PREF_KEY_RECIPE_NAME, context.getString(R.string.ingredients));
        views.setTextViewText(R.id.about_ingredient_tv,recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
    }

    /**
     *
     * @param context of the application
     * @return and intent which will start IngredientWidgetService
     */
    public static Intent getIngredientServiceIntent(Context context){
        return new Intent(context,IngredientWidgetService.class);
    }
}

