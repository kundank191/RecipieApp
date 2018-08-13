package com.example.kunda.bakingapp.ui;

import android.arch.lifecycle.ViewModel;

import com.example.kunda.bakingapp.data.RecipeResponse;

import java.util.List;

/**
 * Created by Kundan on 12-08-2018.
 * <p>
 * Stores the recipe list and helps it share across activity and fragments
 */
public class RecipeViewModel extends ViewModel {

    /**
     * This Constructor will be used by the mainActivity to store the list of Recipe retrieved from the internet
     */
    RecipeViewModel() {

    }

    public List<RecipeResponse> getRecipeList() {
        return mRecipeList;
    }

    public void setRecipeList(List<RecipeResponse> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }

    private List<RecipeResponse> mRecipeList = null;

    /**
     * Inner class in ViewModel which stores a particular recipe details it will be used bt StepList Activity
     * I created this class to create a segregation for various activities which will be using this viewModel
     */
    public RecipeDetails getRecipeDetailsViewModel(RecipeResponse recipe) {
        return new RecipeDetails(recipe);
    }

    public class RecipeDetails {
        private RecipeResponse mRecipeDetails;

        RecipeDetails(RecipeResponse recipeDetails) {
            mRecipeDetails = recipeDetails;
        }

        public void setRecipeDetails(RecipeResponse recipeDetails) {
            mRecipeDetails = recipeDetails;
        }

        public RecipeResponse getAllRecipeDetails() {
            return mRecipeDetails;
        }

        public List<RecipeResponse.Step> getRecipeSteps() {
            return mRecipeDetails.getSteps();
        }

        public List<RecipeResponse.Ingredient> getIngredientsList() {
            return mRecipeDetails.getIngredients();
        }

        public String getRecipeName() {
            return mRecipeDetails.getName();
        }

        public String getRecipeImageURL() {
            return mRecipeDetails.getImage();
        }

        public int getServings() {
            return mRecipeDetails.getServings();
        }
    }
}
