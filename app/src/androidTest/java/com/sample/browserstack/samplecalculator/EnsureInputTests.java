package com.sample.browserstack.samplecalculator;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.percy.espresso.AppPercy;
import io.percy.espresso.lib.ScreenshotOptions;

/**
 * Espresso tests to ensure that editText box is updated appropriately
 * whenever buttons are clicked
 */

@SmallTest
@RunWith(AndroidJUnit4.class)
public class EnsureInputTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity;
    private AppPercy appPercy;

    @Before
    public void setUp() {
        mainActivity = activityRule.getActivity();
        appPercy = new AppPercy();
    }

    @Test
    public void ensureSingleInputIsHandled() {
        onView(withId(R.id.buttonTwo)).perform(click());
        appPercy.screenshot("Single digit");
        onView(withId(R.id.editText)).check(matches(withText("1")));
    }

    @Test
    public void ensureMultipleInputIsHandled() {
        ScreenshotOptions options = new ScreenshotOptions();
        options.setStatusBarHeight(100);
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        appPercy.screenshot("Double digit", options);
        onView(withId(R.id.editText)).check(matches(withText("12")));
    }
}
