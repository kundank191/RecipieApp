package com.example.kunda.bakingapp.ui.details.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 13-08-2018.
 */
public class StepIntroFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.ingredients_rv)
    RecyclerView mRecyclerView;
    IngredientsAdapter mAdapter;
    public static final String ARG_STEP_INTRO = "step_intro";
    private List<RecipeResponse.Ingredient> mIngredientsList;

    public StepIntroFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_STEP_INTRO)){
            RecipeResponse recipe = (RecipeResponse) getArguments().getSerializable(ARG_STEP_INTRO);
            mIngredientsList = recipe.getIngredients();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_intro, container, false);
        ButterKnife.bind(this,rootView);
        initViews();
        return rootView;
    }

    void initViews(){
        mAdapter = new IngredientsAdapter(getActivity(),mIngredientsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
