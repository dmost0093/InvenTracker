package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.IRestrictionsManager;
import comp3350.inventracker.logic.IUserLoginManager;
import comp3350.inventracker.logic.impl.UserLoginManager;

public class UserLoginManagerTest {
    private IUserLoginManager          userLoginManager;
    private IUserLoginsDao             mockUserLoginsDao;
    private IPasswordEncryptionManager mockEncryptionManager;
    private IRestrictionsManager mockRestrictionsManager;
    
    @Before
    public void setUp() {
        mockUserLoginsDao       = mock(IUserLoginsDao.class);
        mockEncryptionManager   = mock(IPasswordEncryptionManager.class);
        mockRestrictionsManager = mock(IRestrictionsManager.class);
    
        userLoginManager = new UserLoginManager(mockUserLoginsDao, mockEncryptionManager, mockRestrictionsManager);
    }
    
    @Test
    public void testValidateUserLogin_ValidCredentials() {
        // Arrange
        String username = "john";
        String password = "password";
        
        UserLoginDto userLoginDto = new UserLoginDto(username, "hash", "salt");
        
        when(mockUserLoginsDao.Select(username.toUpperCase())).thenReturn(userLoginDto);
        when(mockEncryptionManager.VerifyPassword(userLoginDto, password)).thenReturn(true);
        
        // Act
        try {
            userLoginManager.Login(username, password);
        }
        catch (InvalidCredentialsException e) {
            fail("Exception thrown for valid credentials: " + e.getMessage());
        }
        
        // Assert
        verify(mockRestrictionsManager).SetUser(userLoginDto);
        // No exception should be thrown
    }
    
    @Test
    public void testValidateUserLogin_InvalidUsername() {
        // Arrange
        String username = "JOHN";
        String password = "password";
        
        when(mockUserLoginsDao.Select(username)).thenReturn(null);
        
        // Act
        InvalidCredentialsException exception = null;
        try {
            userLoginManager.Login(username, password);
        }
        catch (InvalidCredentialsException e) {
            exception = e;
        }
        
        // Assert
        assertNotNull(exception);
    }
    
    @Test
    public void testValidateUserLogin_InvalidPassword() {
        // Arrange
        String username      = "JOHN";
        String password      = "password";
        String wrongPassword = "wrongPassword";
        
        UserLoginDto userLoginDto = new UserLoginDto(username, "hash", "salt");
        
        when(mockUserLoginsDao.Select(username)).thenReturn(userLoginDto);
        when(mockEncryptionManager.VerifyPassword(userLoginDto, password)).thenReturn(false);
        
        // Act
        InvalidCredentialsException exception = null;
        try {
            userLoginManager.Login(username, wrongPassword);
        }
        catch (InvalidCredentialsException e) {
            exception = e;
        }
        
        // Assert
        assertNotNull(exception);
    }
}
