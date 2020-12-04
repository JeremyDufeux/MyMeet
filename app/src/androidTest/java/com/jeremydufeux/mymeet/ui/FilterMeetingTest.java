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
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.ui.list.ListMeetingActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jeremydufeux.mymeet.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FilterMeetingTest {
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);

    @Test
    public void filterMeetingTest() {
        List<Meeting> meetings = DI.getMeetingApiService().getMeetingList();
        int roomNumber = 3;
        int exceptedMeetingCount = 0;
        for(Meeting meeting : meetings){
            if(meeting.getRoom().getNumber()==roomNumber) exceptedMeetingCount++;
        }

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.list_meetings_filter), withContentDescription(context.getString(R.string.filter)),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction chip2 = onView(
                allOf(withText(context.getString(R.string.room) + " " + roomNumber),
                        childAtPosition(
                                allOf(withId(R.id.dialog_filter_rooms_cpg),
                                        childAtPosition(
                                                withId(R.id.dialog_duration_time_picker),
                                                1)),
                                roomNumber),
                        isDisplayed()));
        chip2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText(context.getString(R.string.ok)),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        onView(ViewMatchers.withId(R.id.list_meetings_rv)).check(withItemCount(exceptedMeetingCount));
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
