package comp3350.inventracker.tests.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.daos.IProductModelDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.exceptions.InvalidCostException;
import comp3350.inventracker.exceptions.InvalidMSRPException;
import comp3350.inventracker.exceptions.ProductModelNameExistsException;
import comp3350.inventracker.logic.IProductModelManager;
import comp3350.inventracker.logic.impl.ProductModelManager;

public class ProductModelManagerTest {
    private IProductModelManager productModelManager;
    private IProductModelDao     productModelDao;
    private ICategoriesDao       categoriesDao;
    
    @Before
    public void setUp() {
        productModelDao = mock(IProductModelDao.class);
        categoriesDao = mock(ICategoriesDao.class);
        productModelManager = new ProductModelManager(productModelDao, categoriesDao);
    }
    
    @Test
    public void testAddProductModel_ValidInput()
        throws CategoryNotFoundException, InvalidCostException, InvalidMSRPException {
        // Arrange
        String name = "Test Product";
        int categoryId = 1;
        BigDecimal cost = new BigDecimal("10.99");
        BigDecimal msrp = new BigDecimal("19.99");
        
        // Set up the mock to return a valid CategoryRowModel for the given categoryId
        CategoryRowModel category = new CategoryRowModel(categoryId, "TestCategory", 100);
        when(categoriesDao.SelectPK(categoryId)).thenReturn(category);
        
        // Act
        productModelManager.AddProductModel(name, categoryId, cost, msrp);
        
        // Assert
        verify(productModelDao, times(1)).Insert(name.toUpperCase(), cost, categoryId, msrp);
    }
    
    @Test(expected = CategoryNotFoundException.class)
    public void testAddProductModel_InvalidCategory() throws CategoryNotFoundException, InvalidCostException, InvalidMSRPException {
        // Arrange
        String name = "Test Product";
        int categoryId = 1;
        BigDecimal cost = new BigDecimal("10.99");
        BigDecimal msrp = new BigDecimal("19.99");
        
        // Set up the mock to return null for the given categoryId (CategoryNotFoundException)
        when(categoriesDao.SelectPK(categoryId)).thenReturn(null);
        
        // Act
        productModelManager.AddProductModel(name, categoryId, cost, msrp);
        
        // Assert
        // Expects CategoryNotFoundException to be thrown
    }
    
    @Test(expected = InvalidCostException.class)
    public void testAddProductModel_InvalidCost() throws CategoryNotFoundException, InvalidCostException, InvalidMSRPException {
        // Arrange
        String name = "Test Product";
        int categoryId = 1;
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal msrp = new BigDecimal("19.99");
        
        // Set up the mock to return a valid CategoryRowModel for the given categoryId
        CategoryRowModel category = new CategoryRowModel(categoryId, "TestCategory", 100);
        when(categoriesDao.SelectPK(categoryId)).thenReturn(category);
        
        // Act
        productModelManager.AddProductModel(name, categoryId, cost, msrp);
        
        // Assert
        // Expects InvalidCostException to be thrown
    }
    
    @Test(expected = InvalidMSRPException.class)
    public void testAddProductModel_InvalidMSRP() throws CategoryNotFoundException, InvalidCostException, InvalidMSRPException {
        // Arrange
        String name = "Test Product";
        int categoryId = 1;
        BigDecimal cost = new BigDecimal("10.99");
        BigDecimal msrp = BigDecimal.ONE;
        
        // Set up the mock to return a valid CategoryRowModel for the given categoryId
        CategoryRowModel category = new CategoryRowModel(categoryId, "TestCategory", 100);
        when(categoriesDao.SelectPK(categoryId)).thenReturn(category);
        
        // Act
        productModelManager.AddProductModel(name, categoryId, cost, msrp);
        
        // Assert
        // Expects InvalidMSRPException to be thrown
    }
    
    @Test(expected = ProductModelNameExistsException.class)
    public void testAddProductModel_DuplicateName() throws CategoryNotFoundException, InvalidCostException, InvalidMSRPException {
        // Arrange
        String name = "Test Product";
        int categoryId = 1;
        BigDecimal cost = new BigDecimal("10.99");
        BigDecimal msrp = new BigDecimal("19.99");
        
        // Set up the mock to return a valid CategoryRowModel for the given categoryId
        CategoryRowModel category = new CategoryRowModel(categoryId, "TestCategory", 100);
        when(categoriesDao.SelectPK(categoryId)).thenReturn(category);
        
        // Set up the mock to throw a PersistenceException (ProductModelNameExistsException)
        when(productModelDao.Insert(name.toUpperCase(), cost, categoryId, msrp))
            .thenThrow(new PersistenceException(new Exception()));
            
        
        // Act
        productModelManager.AddProductModel(name, categoryId, cost, msrp);
        
        // Assert
        // Expects ProductModelNameExistsException to be thrown
    }
}
