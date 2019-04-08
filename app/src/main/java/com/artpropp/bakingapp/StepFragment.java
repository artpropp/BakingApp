package com.artpropp.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artpropp.bakingapp.databinding.FragmentStepBinding;
import com.artpropp.bakingapp.model.Step;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class StepFragment extends Fragment {

    public static final String STEP_EXTRA = "STEP";

    private Step mStep;

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
//        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        FragmentStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        binding.setStep(mStep);

        return binding.getRoot();
    }
}
