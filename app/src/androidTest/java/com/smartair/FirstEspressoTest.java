package com.smartair;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.smartair.app.R;
import com.smartair.app.ui.activities.MainActivity;

public class FirstEspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity activity;

    public FirstEspressoTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testListView() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.scrollTo());
    }
}
