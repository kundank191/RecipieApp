package com.example.kunda.bakingapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;

import java.util.List;

/**
 * Created by Kundan on 28-08-2018.
 */
public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Intent intent;
    private List<RecipeResponse.Ingredient> ingredientList;

    public RemoteViewsFactory(Intent intent, List<RecipeResponse.Ingredient> ingredientList) {
        this.intent = intent;
        this.ingredientList = ingredientList;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RecipeResponse.Ingredient ingredient = ingredientList.get(i);
        RemoteViews remoteViews = new RemoteViews("com.example.kunda.bakingapp", R.layout.ingredient_widget_row_item);
        remoteViews.setTextViewText(R.id.ingredient_description,ingredient.getIngredient());
        return remoteViews;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
