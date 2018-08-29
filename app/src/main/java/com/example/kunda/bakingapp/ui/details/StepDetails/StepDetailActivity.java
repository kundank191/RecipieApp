package com.example.kunda.bakingapp.ui.details.StepDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.Fragments.StepDetailFragment;
import com.example.kunda.bakingapp.ui.details.Fragments.StepIntroFragment;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity {

    private RecipeResponse mRecipe;
    private List<RecipeResponse.Step> mStepList;
    public static String ARG_ALL_STEPS = "all_the_steps";
    private int position;
    @BindView(R.id.next_button)
    Button mNextButton;
    @BindView(R.id.back_button)
    Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(ARG_ALL_STEPS)) {
            mRecipe = (RecipeResponse) getIntent().getSerializableExtra(ARG_ALL_STEPS);
            mStepList = mRecipe.getSteps();
        }
        if (getIntent().hasExtra(StepDetailFragment.ARG_STEP_INFO)) {
            position = getIntent().getIntExtra(StepDetailFragment.ARG_STEP_INFO, 0);
        }
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            //If Step Item was clicked then detail fragment will be shown for that step , else if the ingredient view was clicked the ingredients list will be shown
            if (getIntent().hasExtra(StepDetailFragment.ARG_STEP_INFO)) {
                if (position == mStepList.size() - 1) {
                    mNextButton.setText(getResources().getText(R.string.finish));
                }
                Bundle arguments = new Bundle();
                arguments.putSerializable(StepDetailFragment.ARG_STEP_INFO, mStepList.get(position));
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else if (getIntent().hasExtra(StepIntroFragment.ARG_STEP_INTRO)) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(StepIntroFragment.ARG_STEP_INTRO, getIntent().getSerializableExtra(StepIntroFragment.ARG_STEP_INTRO));
                StepIntroFragment fragment = new StepIntroFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
                mBackButton.setVisibility(View.GONE);
                mNextButton.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param button next button
     *               Implement functionality to easily navigate between steeps
     */
    @OnClick(R.id.next_button)
    public void nextStep(Button button) {
        position += 1;
        if (position == mStepList.size()) {
            finish();
            position -= 1;
        } else if (position == mStepList.size() - 1) {
            button.setText(getResources().getText(R.string.finish));
        }
        Bundle arguments = new Bundle();
        arguments.putSerializable(StepDetailFragment.ARG_STEP_INFO, mStepList.get(position));
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();

    }

    /**
     * @param button back button
     *               Implement functionality to navigate between steps
     */
    @OnClick(R.id.back_button)
    public void previousStep(Button button) {
        position -= 1;
        if (position == -1) {
            finish();
            position = 0;
        }
        Bundle arguments = new Bundle();
        arguments.putSerializable(StepDetailFragment.ARG_STEP_INFO, mStepList.get(position));
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }

}
