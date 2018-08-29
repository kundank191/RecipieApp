package com.example.kunda.bakingapp.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.RecipeViewModel;
import com.example.kunda.bakingapp.ui.ViewModelFactory;
import com.example.kunda.bakingapp.ui.list.IdlingResource.RecipeListIdlingResource;
import com.example.kunda.bakingapp.utils.JSONUtils;
import com.example.kunda.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnDataReceivedListener{

    @BindView(R.id.no_internet_view)
    TextView mNoInternetView;
    @BindView(R.id.error_view)
    TextView mErrorView;
    @BindView(R.id.recipeList_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_view)
    Group mProgressView;
    @BindView(R.id.retry_button)
    FloatingActionButton mRetryButton;
    @BindView(R.id.lottie_view)
    LottieAnimationView mLottieView;
    RecipeAdapter mAdapter;
    private RecipeViewModel mViewModel;
    private ViewModelFactory mFactory;
    @Nullable private RecipeListIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize views
        init();

        //Setting up viewModelFactory and recipeViewModel
        mFactory = new ViewModelFactory();
        mViewModel = ViewModelProviders.of(this, mFactory).get(RecipeViewModel.class);

        if (mViewModel.getRecipeList() != null) {
            populateUI(mViewModel.getRecipeList());
        } else {
            requestData();
        }
    }

    public void init(){
        ButterKnife.bind(this);
        //It enables user to fetch data when something has went wrong like network error
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when user clicks on fab then it will rotate 360 degree clockwise as a touch feedback
                mRetryButton.animate().rotationBy(-360).setDuration(1000).start();
                requestData();
            }
        });
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
        mRetryButton.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new RecipeAdapter(this, recipeResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
     * Only called from test, creates and returns a new {@link RecipeListIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public RecipeListIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipeListIdlingResource();
        }
        return mIdlingResource;
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
        mNoInternetView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.VISIBLE);
    }

    /**
     * When data is being loaded then a loading layout will be shown
     */
    private void showLoadingScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.VISIBLE);
        mLottieView.resumeAnimation();
        mErrorView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.GONE);
    }

    /**
     * If there is no internet connection then a No internet layout will be shown
     */
    private void showNoInternetUI() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
        mNoInternetView.setVisibility(View.VISIBLE);
    }

}
