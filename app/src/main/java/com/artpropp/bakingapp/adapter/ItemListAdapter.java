package com.artpropp.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.bakingapp.R;
import com.artpropp.bakingapp.databinding.ViewHolderIngredientsBinding;
import com.artpropp.bakingapp.databinding.ViewHolderStepBinding;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.model.Step;
import com.artpropp.bakingapp.viewmodel.ItemListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_INGREDIENTS = 0;
    private static final int VIEW_TYPE_STEP = 1;

    private ItemListViewModel mViewModel;

    public ItemListAdapter(ItemListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        Recipe recipe = mViewModel.getRecipe();
        if (recipe == null) return -1;
        if (position == 0 && recipe.getIngredients() != null) {
            return VIEW_TYPE_INGREDIENTS;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                ViewHolderIngredientsBinding ingredientBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_ingredients, parent, false);
                ingredientBinding.setViewModel(mViewModel);
                ingredientBinding.setLifecycleOwner(mViewModel.getLifecycleOwner());
                return new IngredientsViewHolder(ingredientBinding);
            case VIEW_TYPE_STEP:
            default:
                ViewHolderStepBinding stepBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_step, parent, false);
                stepBinding.setViewModel(mViewModel);
                stepBinding.setLifecycleOwner(mViewModel.getLifecycleOwner());
                return new StepViewHolder(stepBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StepViewHolder) {
            StepViewHolder stepViewHolder = (StepViewHolder) holder;
            List<Step> steps = mViewModel.getRecipe().getSteps();
            Step step = steps.get(position-1);
            stepViewHolder.getBinding().setStep(step);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        Recipe recipe = mViewModel.getRecipe();
        if (recipe == null) return count;
        if (recipe.getIngredients() != null) {
             ++count;
        }
        if  (recipe.getSteps() != null) {
            count += recipe.getSteps().size();
        }

        return count;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        ViewHolderIngredientsBinding mBinding;

        IngredientsViewHolder(ViewHolderIngredientsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderIngredientsBinding getBinding() {
            return mBinding;
        }

    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        ViewHolderStepBinding mBinding;

        StepViewHolder(ViewHolderStepBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderStepBinding getBinding() {
            return mBinding;
        }

    }
}
