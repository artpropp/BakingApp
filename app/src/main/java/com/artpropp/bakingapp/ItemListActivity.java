package com.artpropp.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.artpropp.bakingapp.databinding.ActivityItemListBinding;
import com.artpropp.bakingapp.model.Ingredient;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.model.Step;
import com.artpropp.bakingapp.viewmodel.ItemListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.widget.Toast;

import com.artpropp.bakingapp.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ItemListViewModel.OnItemClickListener {

    public static final String RECIPE_EXTRA = "recipe";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(RECIPE_EXTRA)) {
            closeOnError();
            return;
        }

        ItemListViewModel viewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);
        Recipe recipe = intent.getParcelableExtra(RECIPE_EXTRA);
        viewModel.init(this, recipe);

        ActivityItemListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
        }

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            // TODO: Add logic to add ingredients to Widget
        });

        if (binding.getRoot().findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    @Override
    public void onIngredientsClick(String name, List<Ingredient> ingredients) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(IngredientsFragment.INGREDIENTS_EXTRA, (ArrayList<Ingredient>) ingredients);
            arguments.putString(RECIPE_EXTRA, name);
            IngredientsFragment fragment = new IngredientsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putParcelableArrayListExtra(IngredientsFragment.INGREDIENTS_EXTRA, (ArrayList<Ingredient>) ingredients);
            intent.putExtra(RECIPE_EXTRA, name);
            startActivity(intent);
        }
    }

    @Override
    public void onStepClick(Step step) {
        // TODO: show StepFragment
        Log.d("ITEM_LIST", "show step: " + step.getDescription());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
