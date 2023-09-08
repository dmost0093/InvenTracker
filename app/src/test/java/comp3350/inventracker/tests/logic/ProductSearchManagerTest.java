package comp3350.inventracker.tests.logic;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.daos.IProductsDao;
import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.impl.ProductSearchManager;
import comp3350.inventracker.utils.Query;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductSearchManagerTest {
    
    private IProductsDao mockProductsDao;
    private IWarehouseDao mockWarehouseDao;
    private ICategoriesDao       mockCategoriesDao;
    private ProductSearchManager productSearchManager;
    
    @Before
    public void setUp() {
        mockProductsDao = mock(IProductsDao.class);
        mockWarehouseDao = mock(IWarehouseDao.class);
        mockCategoriesDao = mock(ICategoriesDao.class);
        
        productSearchManager = new ProductSearchManager(mockProductsDao, mockWarehouseDao, mockCategoriesDao);
    }
    
    @Test
    public void testGetProductModel_Successful() {
        // Arrange
        int productModelId = 1;
        ProductModelRowModel productModelRowModel = new ProductModelRowModel(productModelId, "Product", 1,
                                                                             BigDecimal.ONE, BigDecimal.TEN);
        when(mockProductsDao.SelectPK(productModelId)).thenReturn(productModelRowModel);
        
        // Act
        ProductModelRowModel result = productSearchManager.GetProductModel(productModelId);
        
        // Assert
        assertEquals(productModelRowModel, result);
        verify(mockProductsDao, times(1)).SelectPK(productModelId);
    }
    
    @Test
    public void testGetWarehouseOptions_Successful() {
        // Arrange
        List<WarehouseRowModel> warehouseList = new ArrayList<>();
        warehouseList.add(new WarehouseRowModel(1, "Warehouse 1", 100));
        warehouseList.add(new WarehouseRowModel(2, "Warehouse 2", 150));
        when(mockWarehouseDao.SelectAll()).thenReturn(new Query<>(warehouseList));
        
        // Act
        List<WarehouseRowModel> result = productSearchManager.GetWarehouseOptions();
        
        // Assert
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).WarehouseID);
        assertEquals("Any", result.get(0).Address);
        assertEquals(0, result.get(0).Capacity);
        assertEquals(1, result.get(1).WarehouseID);
        assertEquals("Warehouse 1", result.get(1).Address);
        assertEquals(100, result.get(1).Capacity);
        assertEquals(2, result.get(2).WarehouseID);
        assertEquals("Warehouse 2", result.get(2).Address);
        assertEquals(150, result.get(2).Capacity);
        verify(mockWarehouseDao, times(1)).SelectAll();
    }
    
    @Test
    public void testGetCategoryOptions_Successful() {
        // Arrange
        List<CategoryRowModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryRowModel(1, "Category 1", 5));
        categoryList.add(new CategoryRowModel(2, "Category 2", 8));
        when(mockCategoriesDao.SelectAll()).thenReturn(new Query<>(categoryList));
        
        // Act
        List<CategoryRowModel> result = productSearchManager.GetCategoryOptions();
        
        // Assert
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).CategoryId);
        assertEquals("Any", result.get(0).CategoryName);
        assertEquals(0, result.get(0).StorageSize);
        assertEquals(1, result.get(1).CategoryId);
        assertEquals("Category 1", result.get(1).CategoryName);
        assertEquals(5, result.get(1).StorageSize);
        assertEquals(2, result.get(2).CategoryId);
        assertEquals("Category 2", result.get(2).CategoryName);
        assertEquals(8, result.get(2).StorageSize);
        verify(mockCategoriesDao, times(1)).SelectAll();
    }
    
    @Test
    public void testGetSortingOptions_Successful() {
        // Arrange
        List<CategoryRowModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryRowModel(1, "Category 1", 5));
        categoryList.add(new CategoryRowModel(2, "Category 2", 8));
        when(mockCategoriesDao.SelectAll()).thenReturn(new Query<>(categoryList));
        
        // Act
        List<CategoryRowModel> result = productSearchManager.GetSortingOptions();
        
        // Assert
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).CategoryId);
        assertEquals("Any", result.get(0).CategoryName);
        assertEquals(0, result.get(0).StorageSize);
        assertEquals(1, result.get(1).CategoryId);
        assertEquals("Category 1", result.get(1).CategoryName);
        assertEquals(5, result.get(1).StorageSize);
        assertEquals(2, result.get(2).CategoryId);
        assertEquals("Category 2", result.get(2).CategoryName);
        assertEquals(8, result.get(2).StorageSize);
        verify(mockCategoriesDao, times(1)).SelectAll();
    }
}
