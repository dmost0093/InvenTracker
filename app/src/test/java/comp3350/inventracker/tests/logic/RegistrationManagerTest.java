package comp3350.inventracker.tests.logic;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IRegistrationManager;
import comp3350.inventracker.logic.impl.RegistrationManager;

public class RegistrationManagerTest {
    
    private IRegistrationManager    registrationManager;
    private IAccountCreationUtility newUserUtility;
    private IUserLoginsDao          loginsDao;
    
    @Before
    public void setUp() {
        newUserUtility = mock(IAccountCreationUtility.class);
        loginsDao = mock(IUserLoginsDao.class);
        registrationManager = new RegistrationManager(loginsDao, newUserUtility);
    }
    
    @Test
    public void testRegisterNewUser_Success() throws RegistrationException {
        // Arrange
        String username = "TESTUSER";
        String password = "testPassword";
        String confirmPassword = "testPassword";
        boolean isAdmin = true;
        
        // Mock behavior for the newUserUtility and loginsDao
        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt");
        when(newUserUtility.CreateNewUser(username, password, isAdmin)).thenReturn(userLoginDto);
        when(loginsDao.Insert(userLoginDto)).thenReturn(1);
        
        // Act
        registrationManager.RegisterNewUser(username, password, confirmPassword, isAdmin);
        
        // Assert
        verify(newUserUtility).CreateNewUser(username, password, isAdmin);
        verify(loginsDao).Insert(userLoginDto);
    }
    
    @Test(expected = RegistrationException.class)
    public void testRegisterNewUser_FailedProcessUserRegistration() throws RegistrationException {
        // Arrange
        String username = "TESTUSER";
        String password = "testPassword";
        String confirmPassword = "testPassword";
        boolean isAdmin = false;
    
        // Mock behavior for the newUserUtility and loginsDao
        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt", isAdmin);
        when(newUserUtility.CreateNewUser(username, password, isAdmin)).thenReturn(userLoginDto);
        when(loginsDao.Insert(userLoginDto)).thenThrow(new PersistenceException(new Exception()));
    
        // Act
        registrationManager.RegisterNewUser(username, password, confirmPassword, isAdmin);
    
        // The test should throw a RegistrationException with the message "Enter a Username!"
    }
    
    @Test(expected = RegistrationException.Username.class)
    public void testRegisterNewUser_ExistingUsername() throws RegistrationException {
        // Arrange
        String username = "TESTUSER";
        String password = "testPassword";
        String confirmPassword = "testPassword";
        boolean isAdmin = false;
    
        // Mock behavior for the newUserUtility and loginsDao
        UserLoginDto userLoginDto = new UserLoginDto(username, "hashedPassword", "salt", isAdmin);
        when(newUserUtility.CreateNewUser(username, password, isAdmin)).thenReturn(userLoginDto);
        when(loginsDao.Insert(userLoginDto)).thenThrow(
            new PersistenceException.IntegrityConstraintViolation(new Exception()));
    
        // Act
        registrationManager.RegisterNewUser(username, password, confirmPassword, isAdmin);
    
        // The test should throw a RegistrationException with the message "Enter a Username!"
    }
    
    @Test(expected = RegistrationException.Username.class)
    public void testRegisterNewUser_FailUsernameValidation() throws RegistrationException {
        // Arrange
        String username = "";
        String password = "testPassword";
        String confirmPassword = "testPassword";
        
        // Act
        registrationManager.RegisterNewUser(username, password, confirmPassword, false);
    }
    
    @Test(expected = RegistrationException.Password.class)
    public void testRegisterNewUser_FailPasswordValidation() throws RegistrationException {
        // Arrange
        String username = "1234";
        String password = "";
        String confirmPassword = "testPassword";
        
        // Act
        registrationManager.RegisterNewUser(username, password, confirmPassword, false);
    }
}
