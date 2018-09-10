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

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;
import com.example.kunda.bakingapp.utils.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 08-08-2018.
 * Shows list of recipe on the main activity
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final Integer[] imageID = {R.drawable.nutella_pie
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

    private final Context mContext;
    private final List<RecipeResponse> mList;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final RecipeResponse recipe = mList.get(i);
        viewHolder.recipeNameTV.setText(recipe.getName());
        viewHolder.servingsTV.setText(String.format(mContext.getString(R.string.servings_text), recipe.getServings()));
        // using glide to set image on the recipe item
        GlideApp.with(mContext)
                .load(recipe.getImage())
                .placeholder(R.drawable.loading_image)
                //Setting hard coded image for a particular recipe
                .error(getAppropriateImageID(i))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(viewHolder.recipeImageIV);
        //set content description on recipe image
        viewHolder.recipeImageIV.setContentDescription(recipe.getName());
        //Setting on click listener on list item to open StepListActivity to Show steps
        viewHolder.listItemRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StepListActivity.class);
                intent.putExtra(StepListActivity.RECIPE_KEY, recipe);
                mContext.startActivity(intent);
            }
        });

    }

    /**
     *
     * The json file is fixed , and only 4 recipes are there so this function will send hard coded pictures of recipe
     * for aesthetic purpose
     * @param position the position of the recipe item
     * @return the appropriate image for the recipe
     */
    private int getAppropriateImageID(int position){
        return imageID[position];
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
