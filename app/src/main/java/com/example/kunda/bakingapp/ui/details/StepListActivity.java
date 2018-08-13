package com.example.kunda.bakingapp.ui.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.RecipeViewModel;
import com.example.kunda.bakingapp.ui.ViewModelFactory;

import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements DetailsItemClickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static String RECIPE_KEY = "getThatRecipe";
    private RecipeResponse mRecipe;
    private ViewModelFactory mFactory;
    private RecipeViewModel mViewModel;
    RecipeViewModel.RecipeDetails mDetailsViewModel;
    private List<RecipeResponse.Step> mListSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        mRecipe = (RecipeResponse) intent.getSerializableExtra(RECIPE_KEY);

        mFactory = new ViewModelFactory();
        mViewModel = ViewModelProviders.of(this,mFactory).get(RecipeViewModel.class);
        mDetailsViewModel = mViewModel.getRecipeDetailsViewModel(mRecipe);

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
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
        Toast.makeText(this,"Position : " + position,Toast.LENGTH_LONG).show();
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(StepDetailFragment.ARG_STEP_INFO,mDetailsViewModel.getRecipeSteps().get(position));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(getBaseContext(), StepDetailActivity.class);
            intent.putExtra(StepDetailFragment.ARG_STEP_INFO,mDetailsViewModel.getRecipeSteps().get(position));
            this.startActivity(intent);
        }
    }
}
