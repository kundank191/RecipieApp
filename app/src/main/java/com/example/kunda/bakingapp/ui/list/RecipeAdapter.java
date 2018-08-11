package com.example.kunda.bakingapp.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 08-08-2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeName)
        TextView recipeNameTV;

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
    public RecipeAdapter(Context context, List<RecipeResponse> recipeResponseList) {
        mList = recipeResponseList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View movieView = LayoutInflater.from(mContext).inflate(R.layout.recipe_row_item, viewGroup, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final RecipeResponse recipe = mList.get(i);
        viewHolder.recipeNameTV.setText(recipe.getName());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
