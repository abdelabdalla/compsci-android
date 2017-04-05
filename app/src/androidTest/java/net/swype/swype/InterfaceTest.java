package net.swype.swype;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InterfaceTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void interfaceTest() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.regBox), withText("register"), isDisplayed()));
        checkBox.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.nameText), isDisplayed()));
        editText.perform(replaceText("tester"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.passText), isDisplayed()));
        editText2.perform(replaceText("testing"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.emailText), isDisplayed()));
        editText3.perform(replaceText("tester@swype.com"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.sendButton), withText("register"), isDisplayed()));
        button.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_create), withContentDescription("create"), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkBox), withText("print when saving"), isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameText), isDisplayed()));
        appCompatEditText.perform(replaceText("test script"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.codeText));
        appCompatEditText2.perform(scrollTo(), replaceText("FWD 2"), closeSoftKeyboard());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_gallery), withContentDescription("my scrypts"), isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.layout),
                        childAtPosition(
                                allOf(withId(R.id.galleryListView),
                                        withParent(withId(R.id.swipeLayout))),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton2.perform(click());

        Espresso.pressBack();

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_explore), withContentDescription("explore"), isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.printButton), withText("print"),
                        withParent(allOf(withId(R.id.layout),
                                childAtPosition(
                                        withId(R.id.galleryListView),
                                        0))),
                        isDisplayed()));
        appCompatButton.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("sign out"), isDisplayed()));
        appCompatTextView.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
