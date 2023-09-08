package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import comp3350.inventracker.daos.IProductSearchDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.InventoryItemDto;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.TrackingRowModel;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.IProductSearchResultsManager;
import comp3350.inventracker.logic.impl.ProductSearchResultsManager;
import comp3350.inventracker.utils.Query;

public class ProductSearchResultsManagerTest {
    
    public static final int PRODUCT_MODEL_ID_1 = 1;
    public static final int PRODUCT_MODEL_ID_2 = 2;
    public static final LocalDate        ARRIVAL_DATE = LocalDate.of(2022, 1, 1);
    public static final LocalDate AS_OF_DATE = LocalDate.of(2023, 7, 31);
    private IProductSearchResultsManager searchManager;
    private IProductSearchDao            mockProductSearchDao;
    private CompleteItemDto item1, item2;
    
    
    final int STORAGE_SIZE = 1;
    final int CATEGORY_1 = 1;
    final int CATEGORY_2 = 2;
    final int INVENTORY_ID_1 = 1;
    final int WAREHOUSE_ID_1 = 1;
    final int INVENTORY_ID_2 = 2;
    
    final CategoryRowModel category1 = new CategoryRowModel(CATEGORY_1, "category1", STORAGE_SIZE);
    
    final ProductModelRowModel product1 = new ProductModelRowModel(
        PRODUCT_MODEL_ID_1, "product1", CATEGORY_1, BigDecimal.valueOf(100), BigDecimal.valueOf(200)
    );
    
    final InventoryRowModel inventory1 = new InventoryRowModel(INVENTORY_ID_1, PRODUCT_MODEL_ID_1);
    final InventoryRowModel inventory2 = new InventoryRowModel(INVENTORY_ID_2, PRODUCT_MODEL_ID_1);
    
    final WarehouseRowModel warehouse1 = new WarehouseRowModel(1, "warehouse1", 100);
    
    final TrackingRowModel tracking1 = new TrackingRowModel(INVENTORY_ID_1, 0, WAREHOUSE_ID_1, ARRIVAL_DATE, null);
    final TrackingRowModel tracking2 = new TrackingRowModel(INVENTORY_ID_2, 0, WAREHOUSE_ID_1, ARRIVAL_DATE, null);
    
    @Before
    public void setUp() {
        mockProductSearchDao = mock(IProductSearchDao.class);
        searchManager        = new ProductSearchResultsManager(mockProductSearchDao);
        
        // Create some sample CompleteItemDto objects for testing
    
        item1 = new CompleteItemDto(inventory1, product1, category1, tracking1, warehouse1);
        item2 = new CompleteItemDto(inventory2, product1, category1, tracking2, warehouse1);
    }
    
    @Test
    public void testGetAvailableItem() {
        // Arrange
        int inventoryId = PRODUCT_MODEL_ID_1;
        
        // Mock the GetAvailableItem method of the DAO to return a CompleteItemDto object
        when(mockProductSearchDao.GetAvailableItem(inventoryId)).thenReturn(item1);
        
        // Act
        CompleteItemDto result = searchManager.GetAvailableItem(inventoryId);
        
        // Assert
        // Ensure the correct method of the mock object is called
        verify(mockProductSearchDao).GetAvailableItem(inventoryId);
        
        // Ensure the correct result is returned
        assertEquals(item1, result);
    }
    
    @Test
    public void testGetAvailableItems() {
        // Arrange
        int inventoryId = PRODUCT_MODEL_ID_1;
        
        // Mock the GetAvailableItem method of the DAO to return a CompleteItemDto object
        List<CompleteItemDto> list = Arrays.asList(item1, item2);
        Query<CompleteItemDto> query = new Query<>(list);
    
        when(mockProductSearchDao.GetAvailableItems(inventoryId, AS_OF_DATE)).thenReturn(query);
        // Act
        searchManager.GetAvailableItems(inventoryId, AS_OF_DATE, null);
    
        // Assert
        // Ensure the correct method of the mock object is called
        verify(mockProductSearchDao).GetAvailableItems(inventoryId, AS_OF_DATE);
    }
    
    @Test
    public void testGetAvailableQuantities_NoFilterNoSort() {
        // Arrange
    
        List<CompleteItemDto> list = Arrays.asList(item1, item2);
        Query<CompleteItemDto> query = new Query<>(list);
        
        when(mockProductSearchDao.GetAllAvailableItems(AS_OF_DATE)).thenReturn(query);
        
        // Act
        List<ProductModelQuantityDto> result = searchManager.GetAvailableQuantities(AS_OF_DATE, null, null);
        
        // Assert
        verify(mockProductSearchDao).GetAllAvailableItems(AS_OF_DATE);
        
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).Quantity);
    }
    
    @Test
    public void testGetAvailableItems_Filter() {
        // Arrange
        int productModelId = PRODUCT_MODEL_ID_1;
        Filter filter = new Filter(0, 0, 0, Integer.MAX_VALUE, true);
    
        List<CompleteItemDto> list = Arrays.asList(item1, item2);
        Query<CompleteItemDto> query = new Query<>(list);
    
    
        // Mock the GetAllAvailableItems method of the DAO to return a list of CompleteItemDto objects
        when(mockProductSearchDao.GetAvailableItems(productModelId, AS_OF_DATE)).thenReturn(query);
        
        // Act
        List<InventoryItemDto> result = searchManager.GetAvailableItems(productModelId, AS_OF_DATE, filter);
        
        // Assert
        // Ensure the correct method of the mock object is called
        verify(mockProductSearchDao).GetAvailableItems(productModelId, AS_OF_DATE);
        
        // Ensure the correct number of results is returned
        assertEquals(2, result.size());
    }
}
