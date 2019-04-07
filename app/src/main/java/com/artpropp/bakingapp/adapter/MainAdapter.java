package com.artpropp.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.bakingapp.R;
import com.artpropp.bakingapp.databinding.ViewHolderRecipeBinding;
import com.artpropp.bakingapp.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {

    private MainViewModel mViewModel;

    public MainAdapter(MainViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_holder_recipe, parent, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(mViewModel.getLifecycleOwner());
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.getBinding().setRecipe(mViewModel.getRecipes().get(position));
    }

    @Override
    public int getItemCount() {
        return mViewModel.getRecipes().size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        ViewHolderRecipeBinding mBinding;

        RecipeViewHolder(ViewHolderRecipeBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderRecipeBinding getBinding() {
            return mBinding;
        }
    }
}
