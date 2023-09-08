package comp3350.inventracker.tests.logic;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.exceptions.CategoryAlreadyExistException;
import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.logic.impl.CategoryEditManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CategoryEditManagerTest {
    
    @Mock
    private ICategoriesDao mockCategoriesDao;
    
    private CategoryEditManager categoryEditManager;
    
    @Before
    public void setUp() {
        // Initialize mock objects and CategoryEditManager
        MockitoAnnotations.initMocks(this);
        categoryEditManager = new CategoryEditManager(mockCategoriesDao);
    }
    
    @Test
    public void testAddCategory_SuccessfulInsert() {
        // Arrange
        String categoryName = "TEST CATEGORY";
        int storageSize = 10;
        
        // Act
        try {
            when(mockCategoriesDao.Insert(categoryName, storageSize)).thenReturn(1); // Mock successful insert
            categoryEditManager.AddCategory(categoryName, storageSize);
            
            // Assert
            // Ensure that the categoriesDao.insert method was called with the correct parameters
            verify(mockCategoriesDao).Insert(categoryName, storageSize);
        } catch (CategoryAlreadyExistException e) {
            fail("Unexpected CategoryAlreadyExistException");
        }
    }
    
    @Test(expected = CategoryAlreadyExistException.class)
    public void testAddCategory_CategoryAlreadyExists() throws CategoryAlreadyExistException {
        // Arrange
        String categoryName = "TEST CATEGORY";
        int storageSize = 10;
        
        // Act
        when(mockCategoriesDao.Insert(categoryName, storageSize)).thenThrow(new PersistenceException(new Exception())); // Mock
        // insert returns 0
        categoryEditManager.AddCategory(categoryName, storageSize);
        
        // Assert
        // Expect a CategoryAlreadyExistException to be thrown
    }
}
