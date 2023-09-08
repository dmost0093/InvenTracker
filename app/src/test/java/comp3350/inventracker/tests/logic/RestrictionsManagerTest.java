package comp3350.inventracker.tests.logic;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.impl.RestrictionsManager;

import static org.junit.Assert.*;

public class RestrictionsManagerTest {
    
    private RestrictionsManager restrictionsManager;
    
    @Before
    public void setUp() {
        // Initialize RestrictionsManager
        restrictionsManager = new RestrictionsManager();
    }
    
    @Test
    public void testSetUser_GetUsername_Successful() {
        // Arrange
        String username = "testUser";
        UserLoginDto user = new UserLoginDto(username, "password", "salt");
        
        // Act
        restrictionsManager.SetUser(user);
        String result = restrictionsManager.getUsername();
        
        // Assert
        assertEquals(username, result);
    }
    
    @Test
    public void testSetUser_IsAdmin_Successful() {
        // Arrange
        UserLoginDto adminUser = new UserLoginDto("admin", "password", "salt", true);
        UserLoginDto regularUser = new UserLoginDto("user", "password", "salt", false);
        
        // Act
        restrictionsManager.SetUser(adminUser);
        boolean isAdmin = restrictionsManager.IsAdmin();
        
        restrictionsManager.SetUser(regularUser);
        boolean isNotAdmin = restrictionsManager.IsAdmin();
        
        // Assert
        assertTrue(isAdmin);
        assertFalse(isNotAdmin);
    }
    
    @Test
    public void testGetUsername_UserIsNull() {
        // Act
        restrictionsManager.SetUser(null);
        String result = restrictionsManager.getUsername();
        
        // Assert
        assertNull(result);
    }
    
    @Test
    public void testIsAdmin_UserIsNull() {
        // Act
        boolean isAdmin = restrictionsManager.IsAdmin();
        
        // Assert
        assertFalse(isAdmin);
    }
}
