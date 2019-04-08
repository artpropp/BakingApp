package com.artpropp.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.bakingapp.R;
import com.artpropp.bakingapp.databinding.ViewHolderIngredientBinding;
import com.artpropp.bakingapp.model.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    List<Ingredient> mIngredients;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderIngredientBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_holder_ingredient, parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.getBinding().setIngredient(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        ViewHolderIngredientBinding mBinding;

        IngredientViewHolder(ViewHolderIngredientBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderIngredientBinding getBinding() {
            return mBinding;
        }
    }

}
