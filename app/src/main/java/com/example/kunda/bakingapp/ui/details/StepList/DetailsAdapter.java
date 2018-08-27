package com.example.kunda.bakingapp.ui.details.StepList;

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

/**
 * Created by Kundan on 13-08-2018.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }

    private final Context mContext;
    private final List<RecipeResponse.Step> mSteps;
    private final boolean mTwoPane;
    private DetailsItemClickListener mListener;

    DetailsAdapter(Context context,
                   List<RecipeResponse.Step> steps,
                   boolean twoPane) {
        mSteps = steps;
        mContext = context;
        mTwoPane = twoPane;
        mListener = (DetailsItemClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsAdapter.ViewHolder holder, int position) {
        final int stepNo = position + 1;
        String stepInfo = "Step " + stepNo + " :";
        holder.mIdView.setText(stepInfo);
        holder.mContentView.setText(mSteps.get(position).getShortDescription());

        holder.itemView.setTag(mSteps.get(position).getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(stepNo - 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

}
