package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.inventracker.daos.hsqldb.CategoriesDao;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.logic.impl.CategoriesManager;
import comp3350.inventracker.logic.ICategoriesManager;
import comp3350.inventracker.tests.utils.TestUtils;

public class CategoriesIT {
    private File               tempDB;
    private ICategoriesManager categoriesManager;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB = TestUtils.copyDB();
        final ICategoriesDao categoriesDao = new CategoriesDao(this.tempDB.getAbsolutePath().replace(".script", ""));
        
        categoriesManager = new CategoriesManager(categoriesDao);
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testGetAllCategories() {
        // Act
        List<CategoryRowModel> result = categoriesManager.GetAllCategories();
        
        // Assert
        assertNotNull(result);
    }
}
