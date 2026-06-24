package com.sample.browserstack.samplecalculator;

// PER-8195 Phase 2 — Espresso advanced example.
// Each @Test exercises one row of the App Percy / Appium Native matrix.
// See advanced/matrix.yml for the canonical mapping.
//
// Espresso tests must live under app/src/androidTest/ (Android
// instrumentation source-set convention). The sibling advanced/ directory
// holds the matrix.yml and README that document this asymmetry.

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import io.percy.espresso.AppPercy;
import io.percy.espresso.lib.ScreenshotOptions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class AdvancedScreenshotTests {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private AppPercy appPercy;

    @Before
    public void setUp() {
        appPercy = new AppPercy();
    }

    @Test
    public void exercisesBaselineScreenshot() {
        onView(withId(R.id.buttonOne)).perform(click());
        appPercy.screenshot("Calculator — single digit");
    }

    @Test
    public void exercisesStatusBarHeight() {
        ScreenshotOptions opts = new ScreenshotOptions();
        opts.setStatusBarHeight(100);
        onView(withId(R.id.buttonOne)).perform(click());
        appPercy.screenshot("Calculator — status bar height", opts);
    }

    @Test
    public void exercisesNavBarHeight() {
        ScreenshotOptions opts = new ScreenshotOptions();
        opts.setNavigationBarHeight(48);
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        appPercy.screenshot("Calculator — nav bar height", opts);
    }

    @Test
    public void exercisesFullscreen() {
        ScreenshotOptions opts = new ScreenshotOptions();
        opts.setFullscreen(true);
        onView(withId(R.id.buttonOne)).perform(click());
        appPercy.screenshot("Calculator — fullscreen", opts);
    }

    @Test
    public void exercisesScreenshotWithBuildMetadataFromEnv() {
        // PERCY_PROJECT / PERCY_BUILD / PERCY_BRANCH / PERCY_COMMIT are read
        // by the AppPercy SDK via System.getenv at upload time; no per-call
        // option needed. This test simply documents the env-driven knobs.
        onView(withId(R.id.buttonOne)).perform(click());
        appPercy.screenshot("Calculator — build metadata via env");
    }
}
