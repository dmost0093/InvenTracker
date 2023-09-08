package comp3350.inventracker.tests.logic;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.IInventoryLogisticsDao;
import comp3350.inventracker.logic.impl.InventoryLogisticsManager;

public class InventoryLogisticsManagerTest {
    private InventoryLogisticsManager logisticsManager;
    private IInventoryLogisticsDao    logisticsDao;
    
    @Before
    public void setUp() {
        logisticsDao = mock(IInventoryLogisticsDao.class);
        logisticsManager = new InventoryLogisticsManager(logisticsDao);
    }
    
    @Test
    public void testImportInventory() throws PersistenceException {
        // Arrange
        int productModelId = 1;
        int toWarehouseId = 2;
        LocalDate asOfDate = LocalDate.now();
        
        // Act
        logisticsManager.ImportInventory(productModelId, toWarehouseId, asOfDate);
        
        // Assert
        verify(logisticsDao).ImportInventory(productModelId, toWarehouseId, asOfDate);
    }
    
    @Test
    public void testExportInventory() throws PersistenceException {
        // Arrange
        int inventoryId = 1;
        LocalDate asOfDate = LocalDate.now();
        
        // Act
        logisticsManager.ExportInventory(inventoryId, asOfDate);
        
        // Assert
        verify(logisticsDao).ExportInventory(inventoryId, asOfDate);
    }
}
