package com.artpropp.bakingapp;

import android.content.Intent;

import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.RecipeReaderUtil;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ItemListActivityTest {

    private Recipe mRecipe = RecipeReaderUtil.getRecipes(ApplicationProvider.getApplicationContext()).get(0);

    @Rule
    public ActivityTestRule<ItemListActivity> mActivityRule =
            new ActivityTestRule<ItemListActivity>(ItemListActivity.class) {

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(ItemListActivity.RECIPE_EXTRA, mRecipe);
                    return intent;
                }

            };

    @Test
    public void verifyToolbarTitle() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(mRecipe.getName())).check(matches(withParent(withId(R.id.toolbar))));
    }

    @Test
    public void clickOnListItem() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.item_detail_container)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnIngredients() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.item_detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnLastStep() {
        final int lastStepPosition = mRecipe.getSteps().size();
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(lastStepPosition, click()));

        onView(withId(R.id.item_detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.description_card_view)).check(matches(isDisplayed()));
    }

    @Test
    public void showStepWithVideo() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.item_detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.video_card_view)).check(matches(isDisplayed()));
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    @Test
    public void showStepDescription() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withText(mRecipe.getSteps().get(0).getDescription())).check(matches(isDisplayed()));
    }

}
