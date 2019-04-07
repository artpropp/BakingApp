package com.artpropp.bakingapp.viewmodel;

import android.app.Application;

import com.artpropp.bakingapp.adapter.MainAdapter;
import com.artpropp.bakingapp.model.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

public class MainViewModel extends AndroidViewModel {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private LifecycleOwner mLifecycleOwner;
    private OnRecipeClickListener mListener;
    private List<Recipe> mRecipes;
    private MainAdapter mAdapter;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAdapter = new MainAdapter(this);
    }

    public void init(LifecycleOwner lifecycleOwner, List<Recipe> recipes) {
        mLifecycleOwner = lifecycleOwner;
        if (mLifecycleOwner instanceof OnRecipeClickListener) {
            mListener = (OnRecipeClickListener) mLifecycleOwner;
        }
        mRecipes = recipes;
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public MainAdapter getAdapter() {
        return mAdapter;
    }

    public void onRecipeClick(int recipeId) {
        for (Recipe recipe: mRecipes) {
            if (recipe.getId() == recipeId && mListener != null) {
                mListener.onRecipeClick(recipe);
            }
        }
    }

}
