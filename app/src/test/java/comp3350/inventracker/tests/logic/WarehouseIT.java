package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.inventracker.daos.hsqldb.WarehouseDao;
import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.IWarehouseManager;
import comp3350.inventracker.logic.impl.WarehouseManager;
import comp3350.inventracker.tests.utils.TestUtils;

public class WarehouseIT {
    private File              tempDB;
    private IWarehouseDao     warehouseDao;
    private IWarehouseManager warehouseManager;
    
    
    @Before
    public void setUp()
        throws IOException {
        // Setup
        tempDB     = TestUtils.copyDB();
        
        warehouseDao     = new WarehouseDao(this.tempDB.getAbsolutePath().replace(".script", ""));
        warehouseManager = new WarehouseManager(warehouseDao);
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testAddWarehouse() {
        // Setup
        final String address = "North Pole";
        final int capacity = 10000;
        final int initialCount = warehouseDao.SelectAll().Count();
        final int warehouseId = initialCount + 1;
        
        // Execute
        warehouseManager.AddWarehouse(address, capacity);
    
        WarehouseRowModel addedWarehouse = warehouseDao.SelectPK(warehouseId);
    
        // Assert
        assertEquals(initialCount + 1, warehouseDao.SelectAll().Count());
        assertEquals(warehouseId, addedWarehouse.WarehouseID);
        assertEquals(address.toUpperCase(), addedWarehouse.Address);
        assertEquals(capacity, addedWarehouse.Capacity);
    }
    
    @Test
    public void testSelectWarehouse() {
        // Setup
        final int warehouseId = 1;
        
        // Execute
        WarehouseRowModel result = warehouseManager.SelectWarehouse(warehouseId);
        
        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testSelectWarehouse_notFound() {
        // Setup
        final int warehouseId = 1234;
    
        // Execute
        WarehouseRowModel result = warehouseManager.SelectWarehouse(warehouseId);
        
        // Assert
        assertNull(result);
    }
    
    @Test
    public void testSelectAllWarehouse() {
        // Execute
        List<WarehouseRowModel> results = warehouseManager.SelectAllWarehouse();
        
        // Assert
        assertNotNull(results);
        results.forEach(result -> {
            assertTrue(result.WarehouseID >= 0);
            assertTrue(result.Capacity >= 0);
            assertNotNull(result.Address);
        });
    }
    
    @Test
    public void testUpdateWarehouse() {
        // Setup
        final int warehouseId = 1;
        final int upgradedSize = 1000000;
    
        WarehouseRowModel initialWarehouse = warehouseDao.SelectPK(warehouseId);
        final int initialSize = initialWarehouse.Capacity;
    
        // Execute
        warehouseManager.UpdateWarehouse(warehouseId, upgradedSize);
        WarehouseRowModel updatedWarehouse = warehouseDao.SelectPK(warehouseId);
        
        // Assert
        assertEquals(warehouseId, updatedWarehouse.WarehouseID);
        assertEquals(upgradedSize, updatedWarehouse.Capacity);
        assertNotEquals(initialSize, updatedWarehouse.Capacity);
    }
}