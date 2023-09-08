package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.impl.AccountCreationUtility;

public class NewUserIT {
    
    private AccountCreationUtility     newUserManager;
    private IPasswordEncryptionManager itEncryptionManager;
    
    @Before
    public void setUp() {
        itEncryptionManager = Services.get(IPasswordEncryptionManager.class);
        newUserManager      = new AccountCreationUtility(itEncryptionManager);
    }
    
    @Test
    public void testCreateNewUser() {
        // Arrange
        String username = "john";
        String password = "password";
        boolean isAdmin = false;
        
        // Act
        UserLoginDto userLoginDto = newUserManager.CreateNewUser(username, password, isAdmin);
        
        // Assert
        assertNotNull(userLoginDto);
        
        assertEquals(username, userLoginDto.Username);
        assertNotNull(userLoginDto.PasswordHash);
        assertNotNull(userLoginDto.PasswordSalt);
        assertEquals(isAdmin, userLoginDto.IsAdmin);
        
        assertEquals(userLoginDto.PasswordHash, itEncryptionManager.HashPassword(password, userLoginDto.PasswordSalt));
    }
}