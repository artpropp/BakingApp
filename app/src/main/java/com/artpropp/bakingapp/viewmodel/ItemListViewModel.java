package com.artpropp.bakingapp.viewmodel;

import android.app.Application;

import com.artpropp.bakingapp.adapter.ItemListAdapter;
import com.artpropp.bakingapp.model.Ingredient;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.model.Step;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

public class ItemListViewModel extends AndroidViewModel {

    public interface OnItemClickListener {
        void onIngredientsClick(String name, List<Ingredient> ingredients);
        void onStepClick(Step step);
    }

    private LifecycleOwner mLifecycleOwner;
    private OnItemClickListener mListener;
    private Recipe mRecipe;
    private ItemListAdapter mAdapter;

    public ItemListViewModel(@NonNull Application application) {
        super(application);
        mAdapter = new ItemListAdapter(this);
    }

    public void init(LifecycleOwner lifecycleOwner, Recipe recipe) {
        mLifecycleOwner = lifecycleOwner;
        if (mLifecycleOwner instanceof OnItemClickListener) {
            mListener = (OnItemClickListener) lifecycleOwner;
        }
        mRecipe = recipe;
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    public Recipe getRecipe() {
        return mRecipe;
    }

    public ItemListAdapter getAdapter() {
        return mAdapter;
    }

    public void onIngredientsClick() {
        if (mListener != null) {
            mListener.onIngredientsClick(mRecipe.getName(), mRecipe.getIngredients());
        }
    }

    public void onStepClicked(int stepId) {
        for (Step step: mRecipe.getSteps()) {
            if (step.getId() == stepId && mListener != null) {
                mListener.onStepClick(step);
            }
        }
    }


}
