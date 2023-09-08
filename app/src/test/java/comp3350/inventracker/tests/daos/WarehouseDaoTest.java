package comp3350.inventracker.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.hsqldb.WarehouseDao;
import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.tests.utils.TestUtils;
import comp3350.inventracker.utils.Query;

public class WarehouseDaoTest {
    private IWarehouseDao warehouseDao;
    private File tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB = TestUtils.copyDB();
        warehouseDao = new WarehouseDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testSelectAll() {
        // Act
        Query<WarehouseRowModel> selectAll = warehouseDao.SelectAll();
        
        // Assert
        assertNotNull(selectAll);
        selectAll.ToList().forEach(x -> {
            assertNotNull(x.Address);
            assertTrue(x.WarehouseID > 0);
            assertTrue(x.Capacity > 0);
        });
    }
    
    @Test
    public void testSelectPK_valid() {
        // Arrange
        final int warehouseId = 1;
        
        // Act
        WarehouseRowModel warehouse = warehouseDao.SelectPK(warehouseId);
        
        // Assert
        assertNotNull(warehouse);
        assertEquals(warehouseId, warehouse.WarehouseID);
    }
    
    @Test
    public void testSelectPK_invalid() {
        // Select existing category
        
        // Arrange
        final int warehouseId = 9999;
        
        // Act
        WarehouseRowModel warehouse = warehouseDao.SelectPK(warehouseId);
        
        // Assert
        assertNull(warehouse);
    }
    
    @Test
    public void testInsert() {
        // Arrange
        final int initialCount = warehouseDao.SelectAll().Count();
    
        String            address  = "Some Address";
        int               capacity = 1000;
    
        // Act
        int rowsUpdated = warehouseDao.Insert(address, capacity);
        
        // Assert
        assertEquals(1, rowsUpdated);
        assertEquals(initialCount + 1, warehouseDao.SelectAll().Count());
    }
    
    @Test
    public void testUpdate_valid() {
        // Arrange
        final int warehouseId = 1;
        final int newCapacity = 10900;
        
        
        // Act
        Exception exception = null;
        try {
            warehouseDao.Update(warehouseId, newCapacity);
        }
        catch (PersistenceException e) {
            exception = e;
        }
        
        // Assert
        assertNull(exception);
        
        assertEquals(newCapacity, warehouseDao.SelectPK(warehouseId).Capacity);
    }
    
    @Test
    public void testUpdate_invalid() {
        // Arrange
        final int warehouseId = 9999;
        final int newCapacity = 10900;
    
    
        // Act
        int rowsUpdated = -1;
        Exception exception = null;
        try {
            rowsUpdated = warehouseDao.Update(warehouseId, newCapacity);
        }
        catch (PersistenceException e) {
            exception = e;
        }
    
        // Assert
        assertNull(exception);
        assertEquals(0, rowsUpdated);
    }
}
