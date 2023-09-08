package comp3350.inventracker.tests.ui.ui;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static comp3350.inventracker.tests.ui.ui.SystemTestUtils.clickWithId;
import static comp3350.inventracker.tests.ui.ui.SystemTestUtils.typeTextInViewWithId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Repositories;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.ui.LoginActivity;

/**
 * Test for:
 * - Login
 * - Logout
 * - Register
 * - Change Password
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserAccountSystemTest {
    
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    
    @Test
    public void testUserAccountFeature() {
        final String username = "registerUsername";
        final String password1 = "registerPassword";
        final String password2 = "registerPassword2";
        
        IUserLoginsDao userLoginsDao = Repositories.getUserLoginsDao();
        userLoginsDao.DeleteUser(username);
        
        // can't login - user doesn't exist yet.
        SystemTestUtils.Login.loginFail(username, password1);
        
        // click register
        clickWithId(R.id.buttonRegister);
        onView(withId(R.id.textTitle)).check(matches(withText(R.string.register)));
        
        // enter details
        typeTextInViewWithId(R.id.username, username);
        clickWithId(R.id.isAdmin);
        typeTextInViewWithId(R.id.password, password1);
        typeTextInViewWithId(R.id.passwordConfirm, password1);
        
        // register
        closeSoftKeyboard();
        clickWithId(R.id.buttonConfirmRegistration);
        onView(withId(R.id.textTitle)).check(matches(withText(R.string.title_activity_login)));
        
        // successfully login with new details.
        SystemTestUtils.Login.loginSuccess(username, password1);
        onView(withId(R.id.buttonAdmin)).check(matches(withText(R.string.admin)));
        onView(withId(R.id.buttonAccount)).check(matches(withText(R.string.account)));
        
        SystemTestUtils.Home.clickAccount();
        SystemTestUtils.Account.clickChangePassword();
        
        // enter details
        typeTextInViewWithId(R.id.oldPassword, password1);
        typeTextInViewWithId(R.id.newPassword, password2);
        typeTextInViewWithId(R.id.passwordConfirm, password2);
        clickWithId(R.id.buttonUpdateAccount);
        
        SystemTestUtils.Account.clickLogout(); // back at account screen
        
        SystemTestUtils.Login.loginFail(username, password1);
        SystemTestUtils.Login.loginSuccess(username, password2);
    }
}