package jworks.letsbake;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by CHUKWU JOHNPAUL on 25/06/17.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LetsBakeTest {
    @Rule
    public ActivityTestRule<FoodItemMainActivity> mActivityTestRule = new ActivityTestRule<>(FoodItemMainActivity.class);

    @Test
    public void BakeTest(){
        ViewInteraction bakeview = onView(allOf(withId(R.id.fooditem_list),isDisplayed()));
        bakeview.perform(actionOnItemAtPosition(1, click()));
    }
}
