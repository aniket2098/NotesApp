package com.example.josh.joshtrainingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.josh.joshtrainingapp.Activity.NavigationHome;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<NavigationHome> mActivityRule =
            new ActivityTestRule<>(NavigationHome.class);

    @Test
    public void ensureNoteAdded() {

        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.editTextTitle))
                .perform(typeText("Espresso Title"));

        onView(withId(R.id.editTextAdd))
                .perform(typeText("Espresso"));

        onView(withId(R.id.floatingActionButton2))
                .perform(click());

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.notesRecycler);

        onView(withId(R.id.notesRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recyclerView.getAdapter().getItemCount() - 1, click()));

        onView(withId(R.id.textViewTitle))
                .check(matches(withText("Espresso Title")));

        onView(withId(R.id.textViewAdd))
                .check(matches(withText("Espresso")));

    }
}