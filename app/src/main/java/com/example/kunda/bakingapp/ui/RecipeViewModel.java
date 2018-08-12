package com.example.kunda.bakingapp.ui;

import android.arch.lifecycle.ViewModel;

import com.example.kunda.bakingapp.data.RecipeResponse;

import java.util.List;

/**
 * Created by Kundan on 12-08-2018.
 *
 * Stores the recipe list and helps it share across activity and fragments
 */
public class RecipeViewModel extends ViewModel {

    public RecipeViewModel(){

    }

    public List<RecipeResponse> getRecipeList() {
        return mRecipeList;
    }

    public void setRecipeList(List<RecipeResponse> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }

    public List<RecipeResponse.Ingredient> getIngredientList(int position){
        return mRecipeList.get(position).getIngredients();
    }

    public List<RecipeResponse.Step> getStepList(int position){
        return mRecipeList.get(position).getSteps();
    }

    private List<RecipeResponse> mRecipeList = null;
}
