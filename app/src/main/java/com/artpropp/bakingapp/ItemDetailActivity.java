package com.artpropp.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        if (intent == null) return;


        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        // Show the Up button in the action bar.
        setupActionBar(intent);

        if (savedInstanceState == null) {
            if (intent.hasExtra(IngredientsFragment.INGREDIENTS_EXTRA)) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(intent.getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, ingredientsFragment)
                        .commit();
            } else if (intent.hasExtra(StepFragment.STEP_EXTRA)) {
                StepFragment stepFragment = new StepFragment();
                stepFragment.setArguments(intent.getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, stepFragment)
                        .commit();
            }
        }

    }

    private void setupActionBar(Intent intent) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (intent.hasExtra(ItemListActivity.RECIPE_EXTRA)) {
                String title = intent.getStringExtra(ItemListActivity.RECIPE_EXTRA);
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
