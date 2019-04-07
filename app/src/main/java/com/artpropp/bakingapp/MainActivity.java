package com.artpropp.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.artpropp.bakingapp.databinding.ActivityMainBinding;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.RecipeReaderUtil;
import com.artpropp.bakingapp.viewmodel.MainViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainViewModel.OnRecipeClickListener {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        List<Recipe> recipes = RecipeReaderUtil.getRecipes(this);
        viewModel.init(this, recipes);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

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
}
