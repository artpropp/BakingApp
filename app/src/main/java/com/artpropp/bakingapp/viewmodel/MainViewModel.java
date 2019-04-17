package com.artpropp.bakingapp.viewmodel;

import android.app.Application;
import android.util.Log;

import com.artpropp.bakingapp.adapter.MainAdapter;
import com.artpropp.bakingapp.api.RecipeApi;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.SimpleIdlingResource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.IdlingResource;
import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends AndroidViewModel {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private static final String TAG = "MAIN_VIEW_MODEL";

    private LifecycleOwner mLifecycleOwner;
    private OnRecipeClickListener mListener;
    private List<Recipe> mRecipes;
    private MainAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable;
    private MutableLiveData<Boolean> mIsLoading;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAdapter = new MainAdapter(this);
        mCompositeDisposable = new CompositeDisposable();
        mIsLoading = new MutableLiveData<>();
    }

    public void init(LifecycleOwner lifecycleOwner, SimpleIdlingResource idlingResource) {
        mLifecycleOwner = lifecycleOwner;
        if (mLifecycleOwner instanceof OnRecipeClickListener) {
            mListener = (OnRecipeClickListener) mLifecycleOwner;
        }

        idlingResource.setIdleState(false);
        mIsLoading.setValue(true);

        mCompositeDisposable.add(
                RecipeApi.getRecipes()
                        .subscribe(
                                recipes -> {
                                    mRecipes = recipes;
                                    mAdapter.notifyDataSetChanged();

                                    mIsLoading.setValue(false);
                                    idlingResource.setIdleState(true);
                                },
                                throwable -> {
                                    Log.e(TAG, throwable.getMessage());

                                    mIsLoading.setValue(false);
                                    idlingResource.setIdleState(true);
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
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
        if (mRecipes == null) return;
        for (Recipe recipe: mRecipes) {
            if (recipe.getId() == recipeId && mListener != null) {
                mListener.onRecipeClick(recipe);
            }
        }
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }


}
