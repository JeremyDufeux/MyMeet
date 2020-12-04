package com.jeremydufeux.mymeet.ui;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.ui.list.ListMeetingActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jeremydufeux.mymeet.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);

    @Test
    public void shouldDisplayMeetingItems() {
        int itemCount = DI.getMeetingApiService().getMeetingList().size();
        onView(ViewMatchers.withId(R.id.list_meetings_rv)).check(withItemCount(itemCount));
    }

    @Test
    public void shouldRemoveOneItem() {
        int itemCount = DI.getMeetingApiService().getMeetingList().size();
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.meeting_item_remove_iv), withContentDescription(context.getString(R.string.remove_meeting)),
                        childAtPosition(
                                allOf(withId(R.id.meeting_item),
                                        childAtPosition(
                                                withId(R.id.list_meetings_rv),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        onView(ViewMatchers.withId(R.id.list_meetings_rv)).check(withItemCount(itemCount-1));
    }

    @Test
    public void shouldOpenAddActivity() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.list_meetings_fab), withContentDescription(context.getString(R.string.add_meeting)),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withText(R.string.add_meeting_title),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        textView.check(matches(withText(context.getString(R.string.add_meeting_title))));
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
