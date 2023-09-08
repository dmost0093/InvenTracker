package comp3350.inventracker.tests.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IPasswordEncryptionManager;

public class PasswordEncryptionManagerTest {
    private IPasswordEncryptionManager encryptionManager;
    
    @Before
    public void setUp() {
        encryptionManager = Services.get(IPasswordEncryptionManager.class);
    }
    
    @Test
    public void testVerifyPassword_ValidPassword() {
        // Arrange
        String username = "john";
        String password = "password";
        String salt     = "salt";
        
        UserLoginDto userLogin = new UserLoginDto(
            username,
            encryptionManager.HashPassword(password, salt),
            salt
        );
        
        // Act
        boolean result = encryptionManager.VerifyPassword(userLogin, password);
        
        // Assert
        assertTrue(result);
    }
    
    @Test
    public void testVerifyPassword_InvalidPassword() {
        // Arrange
        String username = "john";
        String password = "password";
        String wrongPassword = "wrongPassword";
        String salt     = "salt";
    
        UserLoginDto userLogin = new UserLoginDto(
            username,
            encryptionManager.HashPassword(password, salt),
            salt
        );
        
        // Act
        boolean result = encryptionManager.VerifyPassword(userLogin, wrongPassword);
        
        // Assert
        assertFalse(result);
    }
}