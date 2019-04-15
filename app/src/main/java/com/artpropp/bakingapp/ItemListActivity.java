package com.artpropp.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.artpropp.bakingapp.databinding.ActivityItemListBinding;
import com.artpropp.bakingapp.model.Ingredient;
import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.model.Step;
import com.artpropp.bakingapp.util.DataManager;
import com.artpropp.bakingapp.viewmodel.ItemListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
    private ItemListViewModel mViewModel;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Recipe recipe;
        if (intent != null && intent.hasExtra(RECIPE_EXTRA)) {
            recipe = intent.getParcelableExtra(RECIPE_EXTRA);
        } else if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_EXTRA);
        } else {
            closeOnError();
            return;
        }

        mViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);
        mViewModel.init(this, recipe);

        ActivityItemListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        Toolbar toolbar = binding.toolbar;
        setupActionBar(toolbar, recipe.getName());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            Snackbar.make(view, getString(R.string.action_widget_ingredients_selected), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            DataManager.setWidgetRecipeId(this, recipe.getId());
            updateAppWidget();
        });

        if (binding.getRoot().findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void updateAppWidget() {
        Intent intent = new Intent(this, IngredientsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private void setupActionBar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
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
    public void onStepClick(String name, Step step) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepFragment.STEP_EXTRA, step);
            arguments.putString(RECIPE_EXTRA, name);
            StepFragment fragment = new StepFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(StepFragment.STEP_EXTRA, step);
            intent.putExtra(RECIPE_EXTRA, name);
            startActivity(intent);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_EXTRA, mViewModel.getRecipe());
        super.onSaveInstanceState(outState);
    }
}
