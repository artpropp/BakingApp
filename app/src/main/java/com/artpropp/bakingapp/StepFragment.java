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
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    static final String STEP_EXTRA = "STEP";

    private static final String AUTOPLAY = "autoplay";
    private static final String CURRECNT_POSITION = "current_position";

    private boolean mAutoPlay = true;
    private long mCurrentPosition;

    @BindView(R.id.player_view)
    PlayerView mPlayerView;

    @BindView(R.id.video_card_view)
    View mVideoCardView;

    private Step mStep;
    private SimpleExoPlayer mPlayer;

    public StepFragment() { /* default constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_EXTRA)) {
            mStep = getArguments().getParcelable(STEP_EXTRA);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mAutoPlay = savedInstanceState.getBoolean(AUTOPLAY, false);
            mCurrentPosition = savedInstanceState.getLong(CURRECNT_POSITION, 0);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(AUTOPLAY, mAutoPlay);
        outState.putLong(CURRECNT_POSITION, mCurrentPosition);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (mPlayer != null || getContext() == null) return;
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        mPlayer.setPlayWhenReady(mAutoPlay);

        mPlayerView.setPlayer(mPlayer);

        Uri uri = Uri.parse(mStep.getVideoURL());
        MediaSource mediaSource = getMediaSource(uri);
        mPlayer.prepare(mediaSource);

        mPlayer.seekTo(mCurrentPosition);
    }

    private MediaSource getMediaSource(Uri uri) {
        DefaultHttpDataSourceFactory dataSourceFactory =  new DefaultHttpDataSourceFactory("BakingApp");
        return new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer == null) return;

        savePlayerState();

        mPlayer.release();
        mPlayer = null;
    }

    private void savePlayerState() {
        mAutoPlay = mPlayer.getPlayWhenReady();
        mCurrentPosition = mPlayer.getContentPosition();
    }

}
