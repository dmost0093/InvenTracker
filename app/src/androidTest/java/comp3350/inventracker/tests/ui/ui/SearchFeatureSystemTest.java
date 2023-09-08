package comp3350.inventracker.tests.ui.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static comp3350.inventracker.tests.ui.ui.SystemTestUtils.clickWithId;
import static comp3350.inventracker.tests.ui.ui.SystemTestUtils.typeTextInViewWithId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.inventracker.R;
import comp3350.inventracker.ui.LoginActivity;
import comp3350.inventracker.ui.ProductSearchActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchFeatureSystemTest {
    
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    
    @Rule
    public ActivityTestRule<ProductSearchActivity> activityRule = new ActivityTestRule<>(ProductSearchActivity.class);
    
    @Test
    public void testProductSearchActivity() {
        // Test case 1: Enter 0
        String invalidProductId1 = "0";
    
        typeTextInViewWithId(R.id.modelIdInput, invalidProductId1);
        clickWithId(R.id.buttonSearch);
        
        // Test case 2: Enter 99
        String invalidProductId2 = "99";
        typeTextInViewWithId(R.id.modelIdInput, invalidProductId2);
        clickWithId(R.id.buttonSearch);

        // Test case 3: Enter 1
        String validProductId = "1";
        typeTextInViewWithId(R.id.modelIdInput, validProductId);
        clickWithId(R.id.buttonSearch);
        
        // Check that we are now in ProductModelItemsActivity
        onView(withText(R.string.add)).check(matches(withText(R.string.add)));
    }
}