package com.example.kunda.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kunda.bakingapp.ui.details.StepList.StepListActivity;
import com.example.kunda.bakingapp.ui.list.IdlingResource.RecipeListIdlingResource;
import com.example.kunda.bakingapp.ui.list.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Kundan on 29-08-2018.
 * Check if intent opens correct activity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityIntentsTest {

    private static String TEST_ABOUT = "About";
    private RecipeListIdlingResource idlingResource;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = intentsTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void testIntentValidity() {
        onView(withId(R.id.recipeList_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(StepListActivity.class.getName()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
