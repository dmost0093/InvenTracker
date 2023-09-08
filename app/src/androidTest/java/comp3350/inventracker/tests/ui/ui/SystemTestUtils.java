package comp3350.inventracker.tests.ui.ui;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.inventracker.R;

public class SystemTestUtils {
    
    public static void typeTextInViewWithId(int id, String username) {
        onView(withId(id)).perform(clearText());
        onView(withId(id)).perform(typeText(username));
        closeSoftKeyboard();
    }
    
    public static void clickWithId(int id) {
        onView(withId(id)).perform(click());
    }
    
    public static class Login {
    
        public static void loginFail(String username, String password) {
            // Act
            login(username, password);
            onView(withId(R.id.textTitle)).check(matches(withText(R.string.title_activity_login)));
        }
    
        public static void loginSuccess(String username, String password) {
            // Act
            login(username, password);
            onView(withId(R.id.textTitle)).check(matches(withText(R.string.title_activity_home)));
        }
    
        public static void login(String username, String password) {
            typeTextInViewWithId(R.id.username, username);
            typeTextInViewWithId(R.id.password, password);
            clickWithId(R.id.buttonLogin);
        }
    }
    
    public static class Home {
        public static void clickAccount() {
            clickWithId(R.id.buttonAccount);
        }
    }
    
    public static class Account {
        public static void clickChangePassword() {
            clickWithId(R.id.buttonChangePassword);
        }
        
        public static void clickLogout() {
            clickWithId(R.id.buttonLogout);
        }
    }
}