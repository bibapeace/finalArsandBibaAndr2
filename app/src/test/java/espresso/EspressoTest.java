package espresso;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void test(){
        onView(ViewMatchers.withId(R.id.user_field)).perform(ViewActions.typeText("Moscow"));
        onView(ViewMatchers.withId(R.id.main_btn)).perform(ViewActions.click());

        String expected = "Temp";
        String input = result_info.getText().toString();
        input.substring(result_info, 2, 5).check(matches(withText(expected)));
        onView(ViewMatchers.withId(R.id.result_info)).perform(ViewActions.click());

    }


}
