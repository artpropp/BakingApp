package com.artpropp.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artpropp.bakingapp.adapter.IngredientsAdapter;
import com.artpropp.bakingapp.dummy.DummyContent;
import com.artpropp.bakingapp.model.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {

    static final String INGREDIENTS_EXTRA = "ingredients";

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mRecyclerView;

    private IngredientsAdapter mAdatper;

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(INGREDIENTS_EXTRA)) {
            List<Ingredient> ingredients = getArguments().getParcelableArrayList(INGREDIENTS_EXTRA);
            mAdatper = new IngredientsAdapter(ingredients);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            int columnCount = 2;
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columnCount);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mRecyclerView.setAdapter(mAdatper);
    }
}
