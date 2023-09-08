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
import java.time.LocalDate;

import comp3350.inventracker.daos.hsqldb.InventoryDao;
import comp3350.inventracker.daos.hsqldb.InventoryLogisticsDao;
import comp3350.inventracker.daos.hsqldb.TrackingDao;
import comp3350.inventracker.daos.IInventoryDao;
import comp3350.inventracker.daos.IInventoryLogisticsDao;
import comp3350.inventracker.daos.ITrackingDao;
import comp3350.inventracker.dtos.TrackingRowModel;
import comp3350.inventracker.tests.utils.TestUtils;
import comp3350.inventracker.utils.Query;

public class InventoryLogisticsDaoTest {
    public static final LocalDate DATE = LocalDate.of(2023, 6, 2);
    
    private IInventoryLogisticsDao logisticsDao;
    private ITrackingDao           trackingDao;
    private IInventoryDao          inventoryDao;
    private File                   tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB  = TestUtils.copyDB();
        logisticsDao = new InventoryLogisticsDao(this.tempDB.getAbsolutePath().replace(".script", ""));
        trackingDao  = new TrackingDao(this.tempDB.getAbsolutePath().replace(".script", ""));
        inventoryDao = new InventoryDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testImportInventory() {
        // Arrange
        final int productModel = 1;
        final int warehouseId  = 1;
    
        final int initialTrackingCount  = trackingDao.SelectAll().Count();
        final int initialInventoryCount = inventoryDao.SelectAll().Count();
        
        // Act
        logisticsDao.ImportInventory(productModel, warehouseId, DATE);
        
        int inventoryId = trackingDao.SelectAll().Max(x -> x.InventoryId, -1);
        
        // Assert
        assertEquals(initialTrackingCount + 1, trackingDao.SelectAll().Count()); // add row for new location
        assertEquals(initialInventoryCount + 1, inventoryDao.SelectAll().Count()); // add row for new location
        
        Query<TrackingRowModel> path = trackingDao.GetInventoryPath(inventoryId);
        
        path.ToList().forEach(x -> {
            assertNotNull(x.ArrivalDate);
            
            assertEquals(inventoryId, x.InventoryId);
            assertTrue(x.Seq >= 0);
            assertTrue(x.WarehouseId > 0);
        });
        
        assertEquals(1, path.Where(x -> x.DepartDate == null).Count());
        
        final int maxSeq = path.Max(x -> x.Seq, 0);
        
        TrackingRowModel latestInSeq = path.First(x -> x.Seq == maxSeq);
        TrackingRowModel withNoDepart = path.First(x -> x.DepartDate == null);
        
        assertEquals(latestInSeq, withNoDepart);
        
        assertNull(withNoDepart.DepartDate);
        assertNull(latestInSeq.DepartDate);
        assertEquals(DATE, latestInSeq.ArrivalDate);
        assertEquals(warehouseId, latestInSeq.WarehouseId);
    }
    
    @Test
    public void testExportInventory() {
        // Arrange
        final int inventoryId = 1;
    
        final int initialTrackingCount  = trackingDao.SelectAll().Count();
        final int initialInventoryCount = inventoryDao.SelectAll().Count();
    
    
        // Act
        logisticsDao.ExportInventory(inventoryId, DATE);
        
        // Assert
        assertEquals(initialTrackingCount, trackingDao.SelectAll().Count()); // we are simply updating, not deleting.
        assertEquals(initialInventoryCount, inventoryDao.SelectAll().Count()); // we are simply updating, not deleting.
    
        trackingDao.GetInventoryPath(inventoryId).ToList().forEach(x -> {
            assertNotNull(x.DepartDate);
            assertNotNull(x.ArrivalDate);
    
            assertTrue(x.InventoryId > 0);
            assertTrue(x.Seq >= 0);
            assertTrue(x.WarehouseId > 0);
        });
    }
}