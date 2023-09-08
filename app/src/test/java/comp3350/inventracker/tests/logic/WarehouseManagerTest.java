package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.IWarehouseManager;
import comp3350.inventracker.logic.impl.WarehouseManager;
import comp3350.inventracker.utils.Query;

public class WarehouseManagerTest {
    
    private IWarehouseDao mockDao;
    private IWarehouseManager manager;
    
    @Before
    public void setUp() {
        mockDao = mock(IWarehouseDao.class);
        manager = new WarehouseManager(mockDao);
    }
    
    @Test
    public void testAddWarehouse() {
        // Setup
        String address  = "WINNIPEG";
        int    capacity = 10000;
        
        // Execute
        manager.AddWarehouse(address, capacity);
        
        // Assert
        verify(mockDao).Insert(address, capacity);
    }
    
    @Test
    public void testSelectWarehouse() {
        // Setup
        final int warehouseId = 1;
        
        WarehouseRowModel mockWarehouse = new WarehouseRowModel(warehouseId, "Winnipeg", 10000);
        when(mockDao.SelectPK(warehouseId)).thenReturn(mockWarehouse);
        
        // Execute
        WarehouseRowModel result = manager.SelectWarehouse(warehouseId);
        
        // Assert
        verify(mockDao).SelectPK(warehouseId);
        assertEquals(mockWarehouse, result);
    }
    
    @Test
    public void testSelectAllWarehouse() {
        // Setup
        List<WarehouseRowModel> mockWarehouseList = new ArrayList<>();
        when(mockDao.SelectAll()).thenReturn(new Query<>(mockWarehouseList));
        
        // Execute
        List<WarehouseRowModel> result = manager.SelectAllWarehouse();
        
        // Assert
        verify(mockDao).SelectAll();
        assertEquals(mockWarehouseList, result);
    }
    
    @Test
    public void testUpdateWarehouse() {
        // Setup
        int warehouseID = 0;
        int capacity    = 10000;
        
        // Execute
        manager.UpdateWarehouse(warehouseID, capacity);
        
        // Assert
        verify(mockDao).Update(warehouseID, capacity);
    }
}