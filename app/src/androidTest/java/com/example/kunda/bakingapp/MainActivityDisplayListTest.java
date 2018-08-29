package com.example.kunda.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kunda.bakingapp.ui.list.IdlingResource.RecipeListIdlingResource;
import com.example.kunda.bakingapp.ui.list.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Kundan on 29-08-2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityDisplayListTest {

    public static final String TEST_NAME = "Brownies";
    public static String RECIPE_KEY = "getThatRecipe";
    private RecipeListIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkItemName() {
        onView(withText(TEST_NAME)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}

