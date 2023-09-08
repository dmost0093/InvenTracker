package comp3350.inventracker.tests.ui.ui;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.inventracker.R;
import comp3350.inventracker.ui.WarehouseActivity;

import static org.hamcrest.CoreMatchers.anything;

import static comp3350.inventracker.tests.ui.ui.SystemTestUtils.clickWithId;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WarehouseSystemTest {
    
    @Rule
    public ActivityTestRule<WarehouseActivity> activityRule = new ActivityTestRule<>(WarehouseActivity.class);
    
    @Test
    public void testAddWarehouse() {
        clickWithId(R.id.buttonAdd);
        // Enter warehouse information
        onView(withId(R.id.input_warehouseAddress)).perform(typeText("123 Main St"));
        onView(withId(R.id.input_warehouseCapacity)).perform(typeText("1000"));
        
        // Click the "Confirm" button to add the warehouse
        onView(withId(R.id.submitButton)).perform(click());
        
        // Verify that the warehouse was added and the WarehouseActivity appears
        onView(withId(R.id.liveViewWarehouse)).check(matches(isDisplayed()));
    }
    
    @Test
    public void testInvalidInput() {
        clickWithId(R.id.buttonAdd);
        // Enter invalid warehouse capacity input (empty)
        onView(withId(R.id.input_warehouseAddress)).perform(typeText("123 Main St"));
        onView(withId(R.id.input_warehouseCapacity)).perform(typeText(""));
        
        // Click the "Confirm" button to add the warehouse
        onView(withId(R.id.submitButton)).perform(click());
        
        // Verify that the error message is displayed
        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.errorMessage)).check(matches(withText("Failed adding new warehouse because of invalid input")));
        pressBack();
    }
    
    @Test
    public void testSelectWarehouseFromList() {
        // Click on the first warehouse in the list
        onData(anything()).inAdapterView(withId(R.id.liveViewWarehouse)).atPosition(0).perform(click());
        
        // Verify that the WarehouseInfoActivity appears
        onView(withId(R.id.textTitle)).check(matches(withText("Warehouse Information")));
        
        // Verify that the correct warehouse information is displayed
        onView(withId(R.id.WarehouseLocation)).check(matches(isDisplayed()));
        onView(withId(R.id.WarehouseCapacity)).check(matches(isDisplayed()));
        onView(withId(R.id.WarehouseSpaceInUse)).check(matches(isDisplayed()));
        onView(withId(R.id.WarehousePercentFull)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonUpdate)).check(matches(isDisplayed()));
    }
    
    @Test
    public void testWarehouseListClick() {
        // Click on the first warehouse in the list
        onData(anything()).inAdapterView(withId(R.id.liveViewWarehouse)).atPosition(0).perform(click());
        
        // Return to WarehouseActivity
        pressBack();
        
        // Verify that the warehouse list appears
        onView(withId(R.id.liveViewWarehouse)).check(matches(isDisplayed()));
    }
    
    @Test
    public void testUpdateWarehouse() {
        // Wait for the warehouse list to be populated
        onView(withId(R.id.liveViewWarehouse)).check(matches(isDisplayed()));
        
        // Click on the first warehouse in the list
        onData(anything()).inAdapterView(withId(R.id.liveViewWarehouse)).atPosition(0).perform(click());
        
        // Verify that the WarehouseInfoActivity appears
        onView(withId(R.id.textTitle)).check(matches(withText("Warehouse Information")));
    
        // Move to update warehouse page
        clickWithId(R.id.buttonUpdate);
        
        // Update warehouse information
        onView(withId(R.id.input_warehouseCapacity)).perform(replaceText("2000"));
        
        // Click the "Confirm" button to update the warehouse
        onView(withId(R.id.submitButton)).perform(click());
        
        // Return to WarehouseActivity
        pressBack();
        
        // Verify that the updated warehouse information is displayed in the list
        onData(anything()).inAdapterView(withId(R.id.liveViewWarehouse)).atPosition(0)
                          .onChildView(withId(R.id.WarehouseCapacity))
                          .check(matches(withText("2000")));
        
    }
    
    @Test
    public void testInvalidUpdateWarehouse() {
        // Wait for the warehouse list to be populated
        onView(withId(R.id.liveViewWarehouse)).check(matches(isDisplayed()));
        
        // Click on the first warehouse in the list
        onData(anything()).inAdapterView(withId(R.id.liveViewWarehouse)).atPosition(0).perform(click());
        
        // Verify that the WarehouseInfoActivity appears
        onView(withId(R.id.textTitle)).check(matches(withText("Warehouse Information")));
        
        // Move to update warehouse page
        clickWithId(R.id.buttonUpdate);
        
        // Update warehouse information with invalid input (empty address and capacity)
        onView(withId(R.id.input_warehouseCapacity)).perform(replaceText(""));
        
        // Click the "Confirm" button to update the warehouse
        onView(withId(R.id.submitButton)).perform(click());
        
        // Verify that the error message is displayed
        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.errorMessage)).check(matches(withText("Failed updating warehouse with given information.")));
    }
}