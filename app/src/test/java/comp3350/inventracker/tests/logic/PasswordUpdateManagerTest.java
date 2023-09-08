package comp3350.inventracker.tests.logic;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IUserLoginManager;
import comp3350.inventracker.logic.impl.PasswordUpdateManager;

public class PasswordUpdateManagerTest {
    
    private PasswordUpdateManager passwordUpdateManager;
    private IUserLoginsDao loginsDao;
    private IUserLoginManager loginManager;
    private IAccountCreationUtility accountCreationUtility;
    
    @Before
    public void setUp() {
        loginsDao = mock(IUserLoginsDao.class);
        loginManager = mock(IUserLoginManager.class);
        accountCreationUtility = mock(IAccountCreationUtility.class);
        passwordUpdateManager = new PasswordUpdateManager(loginsDao, loginManager, accountCreationUtility);
    }
    
    @Test
    public void testUpdatePassword_Success() throws InvalidCredentialsException, RegistrationException.Password, RegistrationException.PasswordConfirmation {
        // Arrange
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";
        String confirmPassword = "newpassword";
        
        // Mock behavior for loginManager
        doNothing().when(loginManager).Login(username, oldPassword);
        
        // Mock behavior for accountCreationUtility
        UserLoginDto updatedUser = new UserLoginDto(username, "hashednewpassword", "newsalt");
        when(accountCreationUtility.CreateNewUser(username, newPassword)).thenReturn(updatedUser);
        
        // Act
        passwordUpdateManager.UpdatePassword(username, oldPassword, newPassword, confirmPassword);
        
        // Assert
        verify(loginManager).Login(username, oldPassword);
        verify(accountCreationUtility).CreateNewUser(username, newPassword);
        verify(loginsDao).UpdatePassword(updatedUser);
    }
    
    @Test(expected = InvalidCredentialsException.class)
    public void testUpdatePassword_InvalidCredentials() throws InvalidCredentialsException, RegistrationException.Password, RegistrationException.PasswordConfirmation {
        // Arrange
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";
        String confirmPassword = "newpassword";
        
        // Mock behavior for loginManager (throw InvalidCredentialsException)
        doThrow(InvalidCredentialsException.class).when(loginManager).Login(username, oldPassword);
        
        // Act
        passwordUpdateManager.UpdatePassword(username, oldPassword, newPassword, confirmPassword);
        
        // The test should throw an InvalidCredentialsException
    }
    
    @Test(expected = RegistrationException.Password.class)
    public void testUpdatePassword_InvalidNewPassword() throws InvalidCredentialsException, RegistrationException.Password, RegistrationException.PasswordConfirmation {
        // Arrange
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "abc"; // New password is too short
        String confirmPassword = "abc";
        
        // Act
        passwordUpdateManager.UpdatePassword(username, oldPassword, newPassword, confirmPassword);
        
        // The test should throw a RegistrationException.Password
    }
    
    @Test(expected = RegistrationException.PasswordConfirmation.class)
    public void testUpdatePassword_PasswordConfirmationMismatch() throws InvalidCredentialsException, RegistrationException.Password, RegistrationException.PasswordConfirmation {
        // Arrange
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";
        String confirmPassword = "mismatchedpassword"; // Confirm password doesn't match new password
        
        // Act
        passwordUpdateManager.UpdatePassword(username, oldPassword, newPassword, confirmPassword);
        
        // The test should throw a RegistrationException.PasswordConfirmation
    }
    
    // Add more test cases for other scenarios as needed
}


//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import comp3350.inventracker.exceptions.PersistenceException;
//import comp3350.inventracker.daos.IUserLoginsDao;
//import comp3350.inventracker.dtos.UserLoginDto;
//import comp3350.inventracker.exceptions.RegistrationException;
//import comp3350.inventracker.logic.IAccountCreationUtility;
//import comp3350.inventracker.logic.IPasswordUpdateManager;
//import comp3350.inventracker.logic.IRegistrationManager;
//import comp3350.inventracker.logic.IUserLoginManager;
//import comp3350.inventracker.logic.impl.PasswordUpdateManager;
//import comp3350.inventracker.logic.impl.RegistrationManager;
//import comp3350.inventracker.logic.impl.UserLoginManager;
//
//public class PasswordUpdateManagerTest {
//
//    private IPasswordUpdateManager passwordUpdateManager;
//    private IUserLoginManager       loginManager;
//    private IAccountCreationUtility newUserUtility;
//    private IUserLoginsDao          loginsDao;
//
//    @Before
//    public void setUp() {
//        newUserUtility = mock(IAccountCreationUtility.class);
//        loginsDao = mock(IUserLoginsDao.class);
//        loginManager = new UserLoginManager(loginsDao, newUserUtility);
//        passwordUpdateManager = new PasswordUpdateManager(loginsDao, )
//    }
//
//    @Test
//    public void testRegisterNewUser_Success() throws RegistrationException {
//        // Arrange
//        String username = "testUser";
//        String password = "testPassword";
//        String confirmPassword = "testPassword";
//
//        // Mock behavior for the newUserUtility and loginsDao
//        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt");
//        when(newUserUtility.CreateNewUser(username, password)).thenReturn(userLoginDto);
//        when(loginsDao.Insert(userLoginDto)).thenReturn(1);
//
//        // Act
//        registrationManager.RegisterNewUser(username, password, confirmPassword);
//
//        // Assert
//        verify(newUserUtility).CreateNewUser(username, password);
//        verify(loginsDao).Insert(userLoginDto);
//    }
//
//    @Test(expected = RegistrationException.class)
//    public void testRegisterNewUser_FailedProcessUserRegistration() throws RegistrationException {
//        // Arrange
//        String username = "testUser";
//        String password = "testPassword";
//        String confirmPassword = "testPassword";
//
//        // Mock behavior for the newUserUtility and loginsDao
//        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt");
//        when(newUserUtility.CreateNewUser(username, password)).thenReturn(userLoginDto);
//        when(loginsDao.Insert(userLoginDto)).thenThrow(new PersistenceException(new Exception()));
//
//        // Act
//        registrationManager.RegisterNewUser(username, password, confirmPassword);
//
//        // The test should throw a RegistrationException with the message "Enter a Username!"
//    }
//
//    @Test(expected = RegistrationException.Username.class)
//    public void testRegisterNewUser_ExistingUsername() throws RegistrationException {
//        // Arrange
//        String username = "testUser";
//        String password = "testPassword";
//        String confirmPassword = "testPassword";
//
//        // Mock behavior for the newUserUtility and loginsDao
//        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt");
//        when(newUserUtility.CreateNewUser(username, password)).thenReturn(userLoginDto);
//        when(loginsDao.Insert(userLoginDto)).thenThrow(
//            new PersistenceException.IntegrityConstraintViolation(new Exception()));
//
//        // Act
//        registrationManager.RegisterNewUser(username, password, confirmPassword);
//
//        // The test should throw a RegistrationException with the message "Enter a Username!"
//    }
//
//    @Test(expected = RegistrationException.Username.class)
//    public void testRegisterNewUser_FailUsernameValidation() throws RegistrationException {
//        // Arrange
//        String username = "";
//        String password = "testPassword";
//        String confirmPassword = "testPassword";
//
//        // Act
//        registrationManager.RegisterNewUser(username, password, confirmPassword);
//    }
//
//    @Test(expected = RegistrationException.Password.class)
//    public void testRegisterNewUser_FailPasswordValidation() throws RegistrationException {
//        // Arrange
//        String username = "1234";
//        String password = "";
//        String confirmPassword = "testPassword";
//
//        // Act
//        registrationManager.RegisterNewUser(username, password, confirmPassword);
//    }
//}
