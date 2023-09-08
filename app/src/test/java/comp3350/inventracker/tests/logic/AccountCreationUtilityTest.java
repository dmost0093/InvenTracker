package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.impl.AccountCreationUtility;

public class AccountCreationUtilityTest {
    
    private AccountCreationUtility     newUserManager;
    private IPasswordEncryptionManager mockEncryptionManager;
    
    
    @Before
    public void setUp() {
        mockEncryptionManager = mock(IPasswordEncryptionManager.class);
        newUserManager = new AccountCreationUtility(mockEncryptionManager);
    }
    
    @Test
    public void testCreateNewUser() {
        // Arrange
        final String username = "john";
        final String password = "password";
        final String passwordHash = "passwordHash";
        final String passwordSalt = "passwordSalt";
        when(mockEncryptionManager.HashPassword(eq(password), any())).thenReturn(passwordHash);
        when(mockEncryptionManager.GetRandomSalt()).thenReturn(passwordSalt);
        
        // Act
        final UserLoginDto userLoginDto = newUserManager.CreateNewUser(username, password);
        
        // Assert
        assertNotNull(userLoginDto);
        assertEquals(username, userLoginDto.Username);
        assertEquals(passwordHash, userLoginDto.PasswordHash);
        assertEquals(passwordSalt, userLoginDto.PasswordSalt);
        assertNotNull(userLoginDto.PasswordSalt);
    }
}