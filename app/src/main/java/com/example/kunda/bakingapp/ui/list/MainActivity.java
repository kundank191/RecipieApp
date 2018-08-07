package com.example.kunda.bakingapp.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.StepListActivity;
import com.example.kunda.bakingapp.utils.JSONUtils;
import com.example.kunda.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataReceivedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestData();
    }

    /**
     * Makes a call to the getRecipeData function which make a network call to get new data
     */
    private void requestData(){
        NetworkUtils.getRecipeData(this);
    }

    public void clicked(View view){
        startActivity(new Intent(this, StepListActivity.class));
    }

    @Override
    public void onResponse(JSONArray response) {
        List<RecipeResponse> listRecipe = JSONUtils.getRecipeListFromJSON(response);
        Log.d("List Recipe",listRecipe.get(0).getName());
    }

    @Override
    public void onError(String error) {
        Log.d("Error",error);
    }
}
