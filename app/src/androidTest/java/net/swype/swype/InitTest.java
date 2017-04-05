package net.swype.swype;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InitTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void initTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.nameText), withText("username"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.nameText), isDisplayed()));
        editText2.perform(replaceText("abdel"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.passText), isDisplayed()));
        editText3.perform(replaceText("abdel"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.sendButton), withText("login"), isDisplayed()));
        button.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.navigation_create), withContentDescription("create"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                1),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_create), withContentDescription("create"), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameText), isDisplayed()));
        appCompatEditText.perform(replaceText("espresso"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.codeText));
        appCompatEditText2.perform(scrollTo(), replaceText("FWD 30\nRT 90\nFWD 30\nPENDOWN\nFWD 3\nLT 90\nFWD 3\nLT 90\nFWD 3"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkBox), withText("print when saving"), isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_gallery), withContentDescription("my scrypts"), isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.layout),
                        childAtPosition(
                                allOf(withId(R.id.galleryListView),
                                        childAtPosition(
                                                withId(R.id.swipeLayout),
                                                0)),
                                0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.layout),
                        childAtPosition(
                                allOf(withId(R.id.galleryListView),
                                        withParent(withId(R.id.swipeLayout))),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_code_content),
                                childAtPosition(
                                        withId(R.id.v_scroll),
                                        0)),
                        0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton2.perform(click());

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
