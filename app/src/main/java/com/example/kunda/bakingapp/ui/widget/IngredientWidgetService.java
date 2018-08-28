package com.example.kunda.bakingapp.ui.widget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViewsService;

import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;
import com.google.gson.Gson;

/**
 * Created by Kundan on 28-08-2018.
 */
public class IngredientWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String jsonIngredientsList = appSharedPrefs.getString(StepListActivity.PREF_KEY_INGREDIENTS, "");
        RecipeResponse.Ingredient[] ingredientList = gson.fromJson(jsonIngredientsList, RecipeResponse.Ingredient[].class);

        return new com.example.kunda.bakingapp.ui.widget.RemoteViewsFactory(getPackageName(),intent,ingredientList);
    }
}
