package com.example.kunda.bakingapp.ui.details.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kunda.bakingapp.R;
import com.example.kunda.bakingapp.data.RecipeResponse;
import com.example.kunda.bakingapp.ui.details.StepDetails.StepDetailActivity;
import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {

    public static final String ARG_STEP_INFO = "step_details";
    public static final String APP_NAME = "Baking-App";
    private static final String PLAYER_POSITION = "player position";
    private static final long DEFAULT_PLAYER_POSITION = 0;
    RecipeResponse.Step mStepDetails;
    @BindView(R.id.video_player_view)
    PlayerView mMediaPlayerView;
    @BindView(R.id.video_step_description)
    TextView mStepDescription;
    private SimpleExoPlayer player;
    private long position = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    // Get data from arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_STEP_INFO)) {
            mStepDetails = (RecipeResponse.Step) getArguments().getSerializable(ARG_STEP_INFO);
        }
        //Retrieve player position if saved
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(PLAYER_POSITION, DEFAULT_PLAYER_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, rootView);
        mStepDescription.setText(getFormattedDescription(mStepDetails.getDescription()));
        return rootView;
    }

    /**
     * Initialize exoplayer
     */
    @Override
    public void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(getActivity(),
                new DefaultTrackSelector());

        mMediaPlayerView.setPlayer(player);

        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(Objects.requireNonNull(getActivity())
                , Util.getUserAgent(getActivity(), APP_NAME));

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(mStepDetails.getVideoURL()));

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        player.seekTo(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mMediaPlayerView != null) {
            mMediaPlayerView.setPlayer(null);
        }
        if (player != null) {
            player.release();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save player position for config change
        outState.putLong(PLAYER_POSITION, player.getCurrentPosition());
    }

    /**
     * @param description the description of the step
     * @return a formatted for of this description with the number removed
     */
    private String getFormattedDescription(String description) {
        int dotIndex = description.indexOf(".");
        if (dotIndex != -1) {
            int length = description.length();
            return description.substring(dotIndex, length);
        } else {
            return description;
        }
    }
}
