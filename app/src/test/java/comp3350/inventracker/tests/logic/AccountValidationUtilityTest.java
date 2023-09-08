package comp3350.inventracker.tests.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.impl.AccountValidationUtility;

public class AccountValidationUtilityTest {
    
    @Test
    public void testValidateNewUsername_Success() {
        // Arrange
        final String username = "testUser";
        
        // Act
        try {
            AccountValidationUtility.ValidateNewUsername(username);
        }
        catch (final RegistrationException ex) {
            fail();
        }
    }
    
    @Test(expected = RegistrationException.Username.class)
    public void testValidateNewUsername_EmptyUsername() throws RegistrationException {
        // Arrange
        final String username = "";
        
        // Act
        AccountValidationUtility.ValidateNewUsername(username);
    }
    
    @Test(expected = RegistrationException.Username.class)
    public void testValidateNewUsername_ShortUsername() throws RegistrationException {
        // Arrange
        final String username = "123";
        
        // Act
        AccountValidationUtility.ValidateNewUsername(username);
    }
    
    
    @Test
    public void testValidateNewPassword_Success() {
        // Arrange
        final String password        = "testPassword";
        final String confirmPassword = "testPassword";
        
        // Act
        try {
            AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
        }
        catch (final RegistrationException ex) {
            fail();
        }
    }
    
    @Test(expected = RegistrationException.Password.class)
    public void testValidateNewPassword_EmptyPassword() throws RegistrationException {
        // Arrange
        final String password        = "";
        final String confirmPassword = "testPassword";
        
        // Act
        AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
    }
    
    @Test(expected = RegistrationException.Password.class)
    public void testValidateNewPassword_ShortPassword() throws RegistrationException {
        // Arrange
        final String password        = "abc";
        final String confirmPassword = "abc";
        
        // Act
        AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
    }
    
    @Test(expected = RegistrationException.PasswordConfirmation.class)
    public void testValidateNewPassword_ConfirmPasswordMismatch() throws RegistrationException {
        // Arrange
        final String password        = "testPassword";
        final String confirmPassword = "mismatchedPassword";
        
        // Act
        AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
    }
    
    @Test(expected = RegistrationException.PasswordConfirmation.class)
    public void testValidateNewPassword_EmptyConfirmPassword() throws RegistrationException {
        // Arrange
        final String password        = "testPassword";
        final String confirmPassword = "";
        
        // Act
        AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
    }
}
