package com.example.kunda.bakingapp.ui.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 08-08-2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {

    private Integer[] imageID = {R.drawable.nutella_pie
            , R.drawable.brownie
            , R.drawable.yellow_cake
            , R.drawable.cheese_cake};

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeName)
        TextView recipeNameTV;
        @BindView(R.id.recipe_image_iv)
        ImageView recipeImageIV;
        @BindView(R.id.servings_tv)
        TextView servingsTV;
        @BindView(R.id.list_item_recipe)
        ConstraintLayout listItemRecipe;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context mContext;
    private List<RecipeResponse> mList;

    /**
     * @param context            takes the context of the activity
     * @param recipeResponseList the list of the Recipe
     */
    RecipeAdapter(Context context, List<RecipeResponse> recipeResponseList) {
        mList = recipeResponseList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View recipeView = LayoutInflater.from(mContext).inflate(R.layout.recipe_row_item, viewGroup, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i) {

        final RecipeResponse recipe = mList.get(i);
        viewHolder.recipeNameTV.setText(recipe.getName());
        viewHolder.servingsTV.setText(String.format(mContext.getString(R.string.servings_text), recipe.getServings()));
        viewHolder.recipeImageIV.setImageDrawable(mContext.getDrawable(imageID[i]));
        //Setting on click listener on list item to open StepListActivity to Show steps
        viewHolder.listItemRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,StepListActivity.class);
                intent.putExtra(StepListActivity.RECIPE_KEY,recipe);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
