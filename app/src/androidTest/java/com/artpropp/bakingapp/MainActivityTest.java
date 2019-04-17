package com.artpropp.bakingapp;

import com.artpropp.bakingapp.model.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void onCreate() {
        onView(withId(R.id.main_recycler_view)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void onDisplayRecipes() {
        onView(withId(R.id.main_recycler_view)).check(matches(isCompletelyDisplayed()));
        List<Recipe> recipes = mActivityRule.getActivity().mViewModel.getRecipes();
        String recipeName = recipes.get(0).getName();
        onView(withText(recipeName)).check(matches(isDisplayed()));
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
        onView(withId(R.id.main_recycler_view)).check(matches(isCompletelyDisplayed()));
        final int index = 3;
        List<Recipe> recipes = mActivityRule.getActivity().mViewModel.getRecipes();
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.item_list)).check(matches(isDisplayed()));
        onView(withText("Ingredients")).check(matches(isDisplayed()));
        onView(withText(recipes.get(index).getName()))
                .check(matches(withParent(withId(R.id.toolbar))));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}