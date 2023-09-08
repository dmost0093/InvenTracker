package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.inventracker.daos.hsqldb.WarehouseAnalyticsDao;
import comp3350.inventracker.daos.IWarehouseAnalyticsDao;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.logic.impl.WarehouseAnalyticsManager;
import comp3350.inventracker.tests.utils.TestUtils;

public class WarehouseAnalyticsIT {
    private File                      tempDB;
    private WarehouseAnalyticsManager warehouseAnalyticsManager;
    
    @Before
    public void setUp()
        throws IOException {
        // Setup
        this.tempDB     = TestUtils.copyDB();
    
        IWarehouseAnalyticsDao warehouseAnalyticsDao = new WarehouseAnalyticsDao(
            this.tempDB.getAbsolutePath().replace(".script", "")
        );
        
        warehouseAnalyticsManager = new WarehouseAnalyticsManager(warehouseAnalyticsDao);
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testGetAllSpaceInUse() {
        // Execute
        List<WarehouseDto> results = warehouseAnalyticsManager.GetAllSpaceInUse();
        
        // Assert
    
        results.forEach(result -> {
            assertTrue(result.SpaceInUse >= 0);
            assertNotNull(result.Warehouse);
            assertNotNull(result.Warehouse.Address);
        
            assertTrue(result.Warehouse.WarehouseID >= 0);
            assertTrue(result.SpaceInUse <= result.Warehouse.Capacity);
        });
    }
    
    @Test
    public void testGetSpaceInUseAtWarehouse() {
        // Setup
        int warehouseId = 1;
        
        // Execute
        WarehouseDto result = null;
        try {
            result = warehouseAnalyticsManager.GetSpaceInUseAtWarehouse(warehouseId);
        }
        catch (Exception e) {
            Assert.fail("should not throw an exception: " + e.getLocalizedMessage());
        }
        
        // Assert
        assertTrue(result.SpaceInUse >= 0);
        assertNotNull(result.Warehouse);
        assertNotNull(result.Warehouse.Address);
        
        assertEquals(warehouseId, result.Warehouse.WarehouseID);
        assertTrue(result.SpaceInUse <= result.Warehouse.Capacity);
    }
    
    @Test
    public void testGetSpaceInUseAtWarehouse_invalidWarehouse() {
        // Setup
        int warehouseId = 0;
        
        // Execute
        Exception exception = null;
        try {
            warehouseAnalyticsManager.GetSpaceInUseAtWarehouse(warehouseId);
        }
        catch (Exception e) {
            exception = e;
        }
        
        // Assert
        assertNotNull("should throw exception when warehouseId is invalid", exception);
    }
    
    @Test
    public void testGetSpaceInUseAtWarehouse_notFound() {
        // Setup
        int warehouseId = 999;
        
        // Execute
        Exception exception = null;
        try {
            warehouseAnalyticsManager.GetSpaceInUseAtWarehouse(warehouseId);
        }
        catch (Exception e) {
            exception = e;
        }
        
        // Assert
        assertNotNull("should throw exception when warehouseId is not found", exception);
    }
}