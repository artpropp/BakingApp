package com.artpropp.bakingapp;

import com.artpropp.bakingapp.model.Recipe;
import com.artpropp.bakingapp.util.RecipeReaderUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.main_recycler_view)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void onDisplayRecipes() {
        List<Recipe> recipes = RecipeReaderUtil.getRecipes(mActivityRule.getActivity());
        onView(withText(recipes.get(0).getName())).check(matches(isDisplayed()));
    }

    @Test
    public void onRecyclerViewFirstItemClick() {
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.item_list)).check(matches(isDisplayed()));
    }

    @Test
    public void onRecyclerViewThirdItemClick() {
        final int index = 3;
        List<Recipe> recipes = RecipeReaderUtil.getRecipes(mActivityRule.getActivity());
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.item_list)).check(matches(isDisplayed()));
        onView(withText("Ingredients")).check(matches(isDisplayed()));
        onView(withText(recipes.get(index).getName())).check(matches(withParent(withId(R.id.toolbar))));
    }

}