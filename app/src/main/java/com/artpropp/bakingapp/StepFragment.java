package com.artpropp.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artpropp.bakingapp.databinding.FragmentStepBinding;
import com.artpropp.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    static final String STEP_EXTRA = "STEP";

    @BindView(R.id.player_view)
    PlayerView mPlayerView;

    @BindView(R.id.video_card_view)
    View mVideoCardView;

    private Step mStep;
    private SimpleExoPlayer mPlayer;

    public StepFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_EXTRA)) {
            mStep = getArguments().getParcelable(STEP_EXTRA);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        binding.setStep(mStep);
        ButterKnife.bind(this, binding.getRoot());

        if (mStep.getVideoURL() == null || mStep.getVideoURL().isEmpty()) {
            mVideoCardView.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (mPlayer != null || getContext() == null) return;
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        mPlayer.setPlayWhenReady(true);
        mPlayerView.setPlayer(mPlayer);

        Uri uri = Uri.parse(mStep.getVideoURL());
        DefaultHttpDataSourceFactory dataSourceFactory =  new DefaultHttpDataSourceFactory("BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        mPlayer.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (mPlayer == null) return;
        mPlayer.release();
        mPlayer = null;
    }

}
