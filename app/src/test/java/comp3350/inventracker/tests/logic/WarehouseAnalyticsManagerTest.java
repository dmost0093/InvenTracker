package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.inventracker.daos.IWarehouseAnalyticsDao;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.impl.WarehouseAnalyticsManager;
import comp3350.inventracker.utils.Query;

public class WarehouseAnalyticsManagerTest {
    private IWarehouseAnalyticsDao mockDao;
    private WarehouseAnalyticsManager manager;
    
    @Before
    public void setUp() {
        mockDao = mock(IWarehouseAnalyticsDao.class);
        manager = new WarehouseAnalyticsManager(mockDao);
    }
    
    @Test
    public void testGetAllSpaceInUse() {
        // Setup
        List<WarehouseDto> mockWarehouseList = Query.ListOf(
            new WarehouseDto(new WarehouseRowModel(0, "Place 0", 1000), 100),
            new WarehouseDto(new WarehouseRowModel(1, "Place 1", 1000), 200),
            new WarehouseDto(new WarehouseRowModel(2, "Place 2", 1000), 400)
        );
        when(mockDao.GetAllSpaceInUse()).thenReturn(new Query<>(mockWarehouseList));
        
        // Execute
        List<WarehouseDto> result = manager.GetAllSpaceInUse();
        
        // Assert
        verify(mockDao).GetAllSpaceInUse();
        assertEquals(mockWarehouseList, result);
    }
    
    @Test
    public void testGetSpaceInUseAtWarehouse() {
        // Setup
        int warehouseId = 123;
        WarehouseRowModel rowModel = new WarehouseRowModel(123, "No Where", 1000);
        WarehouseDto mockWarehouseDto = new WarehouseDto(rowModel, 100);
        
        when(mockDao.GetSpaceInUseAtWarehouse(warehouseId)).thenReturn(mockWarehouseDto);
        
        // Execute
        WarehouseDto result = null;
        try {
            result = manager.GetSpaceInUseAtWarehouse(warehouseId);
        }
        catch (Exception e) {
            Assert.fail("should not throw an exception");
        }
        
        // Assert
        verify(mockDao).GetSpaceInUseAtWarehouse(warehouseId);
        assertEquals(mockWarehouseDto, result);
    }
}