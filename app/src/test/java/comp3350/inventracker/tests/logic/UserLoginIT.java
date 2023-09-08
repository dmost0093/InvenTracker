package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.inventracker.app.Services;
import comp3350.inventracker.daos.hsqldb.UserLoginsDao;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.IUserLoginManager;
import comp3350.inventracker.logic.impl.RestrictionsManager;
import comp3350.inventracker.logic.impl.UserLoginManager;
import comp3350.inventracker.tests.utils.TestUtils;

public class UserLoginIT {
    private File                       tempDB;
    private IUserLoginManager userLoginManager;
    
    @Before
    public void setUp()
        throws IOException {
        // Setup
        this.tempDB     = TestUtils.copyDB();
        
        IUserLoginsDao             itUserLoginsDao     = new UserLoginsDao(this.tempDB.getAbsolutePath()
                                                                                      .replace(".script", ""));
        IPasswordEncryptionManager itEncryptionManager = Services.get(IPasswordEncryptionManager.class);
    
        userLoginManager = new UserLoginManager(itUserLoginsDao, itEncryptionManager, new RestrictionsManager());
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testValidateUserLogin_ValidCredentials() {
        // Arrange
        String username = "admin";
        String password = "pass";
        
        // Act
        Exception exception = null;
        try {
            userLoginManager.Login(username, password);
        }
        catch (InvalidCredentialsException e) {
            exception = e;
        }
        
        // Assert
        assertNull(exception);
    }
    
    @Test
    public void testValidateUserLogin_InvalidUsername() {
        // Arrange
        String username = "not_admin";
        String password = "pass";
        
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
        String username      = "admin";
        String password      = "wrongPassword";
        
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
}
