package com.example.kunda.bakingapp.ui.details.StepList;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.RecipeViewModel;
import com.example.kunda.bakingapp.ui.ViewModelFactory;
import com.example.kunda.bakingapp.ui.details.Fragments.StepDetailFragment;
import com.example.kunda.bakingapp.ui.details.Fragments.StepIntroFragment;
import com.example.kunda.bakingapp.ui.details.StepDetails.StepDetailActivity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements DetailsItemClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static String RECIPE_KEY = "getThatRecipe";
    public static String PREF_KEY_INGREDIENTS  = "Ingredients list";
    public static String PREF_KEY_RECIPE_NAME = "Recipe Name";
    private RecipeResponse mRecipe;
    private ViewModelFactory mFactory;
    private RecipeViewModel mViewModel;
    RecipeViewModel.RecipeDetails mDetailsViewModel;
    private List<RecipeResponse.Step> mListSteps;
    private Context mContext;
    @BindView(R.id.intro_group_view)
    LinearLayout mAboutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //The Recipe object passed though intent will be received
        Intent intent = getIntent();
        mRecipe = (RecipeResponse) intent.getSerializableExtra(RECIPE_KEY);

        mFactory = new ViewModelFactory();
        mViewModel = ViewModelProviders.of(this,mFactory).get(RecipeViewModel.class);
        //The recipe object will be used to initialize viewModel , the viewModel for this activity is a subclass in RecipeViewModel
        mDetailsViewModel = mViewModel.getRecipeDetailsViewModel(mRecipe);
        //After setting up view model the list of steps will be derived from there
        mListSteps = mDetailsViewModel.getRecipeSteps();

        setTitle(mDetailsViewModel.getRecipeName());

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        initViews();
        startCooking();
    }

    private void initViews(){
        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        mContext = this;
        ButterKnife.bind(this);

        // Initially when Step List activity is opened in tablet then detail fragment will show ingredients
        if(mTwoPane)
            showIngredientsList();

        mAboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    showIngredientsList();
                } else {
                    Intent intent = new Intent(getBaseContext(), StepDetailActivity.class);
                    intent.putExtra(StepIntroFragment.ARG_STEP_INTRO,mDetailsViewModel.getAllRecipeDetails());
                    mContext.startActivity(intent);
                }

            }
        });
    }

    private void showIngredientsList(){
        Bundle arguments = new Bundle();
        arguments.putSerializable(StepIntroFragment.ARG_STEP_INTRO,mDetailsViewModel.getAllRecipeDetails());
        StepIntroFragment fragment = new StepIntroFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }

    /**
     * The recipe on which the user will click will be saved in preferences and this recipe's ingredients will be shown in the widget
     */
    private void startCooking(){

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        Gson gson = new Gson();
        String ingredientsListJson = gson.toJson(mDetailsViewModel.getIngredientsList());
        prefsEditor.putString(PREF_KEY_INGREDIENTS, ingredientsListJson);
        prefsEditor.putString(PREF_KEY_RECIPE_NAME,mDetailsViewModel.getRecipeName());
        prefsEditor.apply();

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new DetailsAdapter(this, mDetailsViewModel.getRecipeSteps(), mTwoPane));
    }

    /**
     * This method will be triggered when a recycler view item will be clicked
     * @param position of the item which was clicked
     */
    @Override
    public void onItemClick(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(StepDetailFragment.ARG_STEP_INFO,mListSteps.get(position));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(getBaseContext(), StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.ARG_ALL_STEPS,mDetailsViewModel.getAllRecipeDetails());
            intent.putExtra(StepDetailFragment.ARG_STEP_INFO,position);
            this.startActivity(intent);
        }
    }
}
