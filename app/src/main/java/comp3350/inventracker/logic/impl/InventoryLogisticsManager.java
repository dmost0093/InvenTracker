package comp3350.inventracker.logic.impl;

import java.time.LocalDate;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.IInventoryLogisticsDao;
import comp3350.inventracker.logic.IInventoryLogisticsManager;

public class InventoryLogisticsManager
    implements IInventoryLogisticsManager {
    
    final IInventoryLogisticsDao logisticsDao;
    
    public InventoryLogisticsManager(IInventoryLogisticsDao logisticsDao) {
        this.logisticsDao = logisticsDao;
    }
    
    @Override
    public void ImportInventory(int productModelId, int toWarehouseId, LocalDate asOfDate)
        throws PersistenceException {
        logisticsDao.ImportInventory(productModelId, toWarehouseId, asOfDate);
    }
    
    @Override
    public void TransportInventory(int inventoryId, int toWarehouseId, LocalDate asOfDate)
        throws PersistenceException {
        logisticsDao.TransportInventory(inventoryId, toWarehouseId, asOfDate);
    }
    
    @Override
    public void ExportInventory(int inventoryId, LocalDate asOfDate)
        throws PersistenceException {
        logisticsDao.ExportInventory(inventoryId, asOfDate);
    }
}
