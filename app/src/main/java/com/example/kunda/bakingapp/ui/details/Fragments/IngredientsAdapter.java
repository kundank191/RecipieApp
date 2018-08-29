package com.example.kunda.bakingapp.ui.details.Fragments;

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
 * Created by Kundan on 14-08-2018.
 * Adapter to show list of ingredients in recycler view
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name_tv)
        TextView ingredientNameTV;
        @BindView(R.id.ingredient_measure_tv)
        TextView ingredientMeasureTV;
        @BindView(R.id.ingredient_quantity_tv)
        TextView ingredientQuantityTV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context mContext;
    private List<RecipeResponse.Ingredient> mList;

    /**
     * @param context         the context of the activity
     * @param ingredientsList the list of ingredients to be displayed
     */
    IngredientsAdapter(Context context, List<RecipeResponse.Ingredient> ingredientsList) {
        mList = ingredientsList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(mContext).inflate(R.layout.ingredients_row_item, parent, false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecipeResponse.Ingredient ingredient = mList.get(position);

        holder.ingredientNameTV.setText(ingredient.getIngredient());
        holder.ingredientMeasureTV.setText(ingredient.getMeasure());
        String quantity = ingredient.getQuantity() + "";
        holder.ingredientQuantityTV.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
