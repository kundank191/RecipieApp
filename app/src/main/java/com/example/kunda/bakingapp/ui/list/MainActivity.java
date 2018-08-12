package com.example.kunda.bakingapp.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.RecipeViewModel;
import com.example.kunda.bakingapp.ui.ViewModelFactory;
import com.example.kunda.bakingapp.utils.JSONUtils;
import com.example.kunda.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnDataReceivedListener {

    @BindView(R.id.no_internet_view)
    TextView mNoInternetView;
    @BindView(R.id.error_view)
    TextView mErrorView;
    @BindView(R.id.recipeList_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_view)
    Group mProgressView;
    RecipeAdapter mAdapter;
    private RecipeViewModel mViewModel;
    private ViewModelFactory mFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Setting up viewModelFactory and recipeViewModel
        mFactory = new ViewModelFactory();
        mViewModel = ViewModelProviders.of(this, mFactory).get(RecipeViewModel.class);

        if (mViewModel.getRecipeList() != null) {
            populateUI(mViewModel.getRecipeList());
        } else {
            requestData();
        }
    }

    /**
     * Makes a network call and gets data from internet
     */
    private void requestData() {
        if (isNetworkConnected()) {
            showLoadingScreen();
            NetworkUtils.getRecipeData(this);
        } else {
            //Complete it
            showNoInternetUI();
        }
    }

    private void populateUI(List<RecipeResponse> recipeResponseList) {

        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new RecipeAdapter(this, recipeResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setVerticalFadingEdgeEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * This function gets the response from the internet converts it into a list of recipe
     * then updates the viewModel and then the UI
     *
     * @param response from the internet
     */
    @Override
    public void onResponse(JSONArray response) {
        List<RecipeResponse> listRecipe = JSONUtils.getRecipeListFromJSON(response);
        Log.d("List Recipe", listRecipe.get(0).getName());
        mViewModel.setRecipeList(listRecipe);
        populateUI(mViewModel.getRecipeList());
    }

    /**
     * If some error is thrown during network data request
     *
     * @param error which was thrown
     */
    @Override
    public void onError(String error) {
        Log.d("Error", error);
        showErrorUI();
    }

    /**
     * It checks if the device is connected to the internet or not
     *
     * @return a boolean value indicating the state of network
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * If an error occurs then an error layout will be shown
     */
    private void showErrorUI() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * When data is being loaded then a loading layout will be shown
     */
    private void showLoadingScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * If there is no internet connection then a No internet layout will be shown
     */
    private void showNoInternetUI() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.VISIBLE);
    }
}
