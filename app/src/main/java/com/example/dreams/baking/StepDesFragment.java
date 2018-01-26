package com.example.dreams.baking;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dreams.baking.models.StepsModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class StepDesFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.StepFullDescription)
    TextView mStepDescription;
    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.step_image_view)
    ImageView mStepImageView;
    SimpleExoPlayer exoPlayer;
    Context mContext;
    StepsModel mStepsModel;
    @BindBool(R.bool.two_pane_mode)
    boolean isTwoPane;

    long mPlayerPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, rootView);
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            mExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            mStepDescription.setVisibility(View.GONE);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStepsModel = getArguments().getParcelable("step");
        mStepDescription.setText(mStepsModel != null ? mStepsModel.getDescription() : "No Data");
        if (!mStepsModel.getThumbnailURL().isEmpty()) {
            mStepImageView.setVisibility(View.GONE);
            Picasso.with(mContext)
                    .load(mStepsModel.getThumbnailURL())
                    .resize(50, 50)
                    .centerCrop()
                    .into(mStepImageView);
        } else
            mStepImageView.setVisibility(View.VISIBLE);

        if (mStepsModel.getVideoURL().isEmpty())
            mExoPlayerView.setVisibility(View.GONE);
        else
            exo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unbind the view to free some memory
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null)
            outState.putLong("position_key", exoPlayer.getCurrentPosition());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            mPlayerPosition = savedInstanceState.getLong("position_key", 0);
        if (mPlayerPosition != 0)
            exo();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null)
            exoPlayer.release();
    }

    public void exo() {
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

            Uri videoURI = Uri.parse(mStepsModel.getVideoURL());

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoPlayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            mExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(mPlayerPosition);
            exoPlayer.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e("Baking app", " exoPlayer error " + e.toString());
        }
    }

}
