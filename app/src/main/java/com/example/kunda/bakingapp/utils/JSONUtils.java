package com.example.kunda.bakingapp.utils;

import com.example.kunda.bakingapp.data.RecipeResponse;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kundan on 07-08-2018.
 */
public class JSONUtils {

    /**
     *
     * @param jsonArray raw json data
     * @return list of Recipe
     */
    public static List<RecipeResponse> getRecipeListFromJSON(JSONArray jsonArray){
        RecipeResponse[] listRecipe = null;
        Gson gson = new Gson();
        listRecipe = gson.fromJson(jsonArray.toString(),RecipeResponse[].class);
        return Arrays.asList(listRecipe);
    }
}
