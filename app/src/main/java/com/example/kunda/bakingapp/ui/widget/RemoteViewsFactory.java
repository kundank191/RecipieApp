package com.example.kunda.bakingapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;

/**
 * Created by Kundan on 28-08-2018.
 * This class will be used to populate the list view of the widget
 */
public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    // Not sure why this intent will be used
    private Intent intent;
    private RecipeResponse.Ingredient[] ingredientList;
    private String packageName;

    RemoteViewsFactory(Intent intent, RecipeResponse.Ingredient[] ingredientList, String packageName) {
        this.intent = intent;
        this.ingredientList = ingredientList;
        this.packageName = packageName;
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
        return ingredientList.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RecipeResponse.Ingredient ingredient = ingredientList[i];
        RemoteViews remoteViews = new RemoteViews(packageName, R.layout.ingredient_widget_row_item);
        //set title
        remoteViews.setTextViewText(R.id.ingredient_description, getFormattedText(ingredient));
        return remoteViews;
    }

    /**
     * @param ingredient the ingredient to be displayed in the list
     * @return a formatted description of the ingredient
     */
    private String getFormattedText(RecipeResponse.Ingredient ingredient) {
        String text;
        String quantity;
        String DEFAULT_VALUE_NO_UNIT = "UNIT";

        //Check if amount is an integer
        double variable = ingredient.getQuantity();
        if ((variable == Math.floor(variable)) && !Double.isInfinite(variable)) {
            // integer type
            quantity = String.valueOf(variable);
            int length = quantity.length();
            quantity = quantity.substring(0, length - 2);
        } else {
            quantity = String.valueOf(variable);
        }

        // Check is measure is a number or has a unit
        if (ingredient.getMeasure().equals(DEFAULT_VALUE_NO_UNIT)) {
            text = quantity + " " + ingredient.getIngredient();
        } else {
            text = quantity + " " + ingredient.getMeasure() + " of " + ingredient.getIngredient();
        }
        return text;
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
