package com.artpropp.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.artpropp.bakingapp.databinding.ActivityMainBinding;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.SimpleIdlingResource;
import com.artpropp.bakingapp.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

public class MainActivity extends AppCompatActivity implements MainViewModel.OnRecipeClickListener {

    MainViewModel mViewModel;
    RecyclerView mRecyclerView;

    SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIdlingResource();
        if (mViewModel == null) {
            mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mViewModel.init(this, mIdlingResource);
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        mRecyclerView = binding.mainRecyclerView;
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            int columnCount = 2;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                columnCount = 3;
            }
            GridLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, ItemListActivity.class);
        intent.putExtra(ItemListActivity.RECIPE_EXTRA, recipe);
        startActivity(intent);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
