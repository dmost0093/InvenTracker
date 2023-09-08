package comp3350.inventracker.tests.logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.logic.impl.CategoriesManager;
import comp3350.inventracker.logic.ICategoriesManager;
import comp3350.inventracker.utils.Query;

public class CategoriesManagerTest {
    private ICategoriesManager categoriesManager;
    private ICategoriesDao     categoriesDao;
    
    @Before
    public void setUp() {
        categoriesDao = mock(ICategoriesDao.class);
        categoriesManager = new CategoriesManager(categoriesDao);
    }
    
    @Test
    public void testGetAllCategories() {
        // Arrange
        List<CategoryRowModel> expectedCategories = new ArrayList<>();
        expectedCategories.add(new CategoryRowModel(1, "Category 1", 5));
        expectedCategories.add(new CategoryRowModel(2, "Category 2", 10));
        when(categoriesDao.SelectAll()).thenReturn(new Query<>(expectedCategories));
        
        // Act
        List<CategoryRowModel> result = categoriesManager.GetAllCategories();
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedCategories.size(), result.size());
        assertEquals(expectedCategories.get(0).CategoryId, result.get(0).CategoryId);
        assertEquals(expectedCategories.get(0).CategoryName, result.get(0).CategoryName);
        assertEquals(expectedCategories.get(0).StorageSize, result.get(0).StorageSize);
        assertEquals(expectedCategories.get(1).CategoryId, result.get(1).CategoryId);
        assertEquals(expectedCategories.get(1).CategoryName, result.get(1).CategoryName);
        assertEquals(expectedCategories.get(1).StorageSize, result.get(1).StorageSize);
    }
}
