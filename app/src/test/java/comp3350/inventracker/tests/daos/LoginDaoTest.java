package comp3350.inventracker.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.inventracker.daos.hsqldb.UserLoginsDao;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.tests.utils.TestUtils;

public class LoginDaoTest {
    private IUserLoginsDao userLoginsDao;
    private File           tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB   = TestUtils.copyDB();
        userLoginsDao = new UserLoginsDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }

    @Test
    public void testSelectPK_valid() {
        // Arrange
        final String username = "ADMIN";
        
        // Act
        UserLoginDto userLogin = userLoginsDao.Select(username);
        
        // Assert
        assertNotNull(userLogin);
        assertEquals(username, userLogin.Username);
    }
    
    @Test
    public void testSelectPK_invalid() {
        // Arrange
        final String username = "does not exist";
        
        // Act
        UserLoginDto userLogin = userLoginsDao.Select(username);
        
        // Assert
        assertNull(userLogin);
    }
}