package comp3350.inventracker.tests.daos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.inventracker.daos.hsqldb.CategoriesDao;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.tests.utils.TestUtils;
import comp3350.inventracker.utils.Query;

import static org.junit.Assert.*;

public class CategoryDaoTest {
    private ICategoriesDao categoriesDao;
    private File               tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB = TestUtils.copyDB();
        categoriesDao = new CategoriesDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }

    @Test
    public void testSelectAll() {
        // Act
        Query<CategoryRowModel> selectAll = categoriesDao.SelectAll();
    
        // Assert
        assertNotNull(selectAll);
        selectAll.ToList().forEach(x -> {
            assertNotNull(x.CategoryName);
            assertTrue(x.CategoryId > 0);
            assertTrue(x.StorageSize > 0);
        });
    }

    @Test
    public void testSelectPK_valid() {
        // Arrange
        final int categoryId = 1;
        
        // Act
        CategoryRowModel categoryRowModel = categoriesDao.SelectPK(categoryId);
        
        // Assert
        assertNotNull(categoryRowModel);
        assertEquals(categoryId, categoryRowModel.CategoryId);
    }
    
    @Test
    public void testSelectPK_invalid() {
        // Arrange
        final int categoryId = 9999;
        
        // Act
        CategoryRowModel categoryRowModel = categoriesDao.SelectPK(categoryId);
        
        // Assert
        assertNull(categoryRowModel);
    }

    @Test
    public void testInsert() {
        // Arrange
        final int initialCount = categoriesDao.SelectAll().Count();
        final String categoryName = "New Category";
        final int storageSize = 35;
        
        // Act
        int rowsUpdated = categoriesDao.Insert(categoryName, storageSize);
        
        // Assert
        assertEquals(1, rowsUpdated);
        assertEquals(initialCount + 1, categoriesDao.SelectAll().Count());
    }
}