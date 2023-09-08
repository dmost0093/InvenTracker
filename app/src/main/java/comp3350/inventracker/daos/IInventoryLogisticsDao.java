package comp3350.inventracker.daos;

import java.time.LocalDate;

import comp3350.inventracker.exceptions.PersistenceException;

public interface IInventoryLogisticsDao {
    void ImportInventory(int productModelId, int toWarehouseId, LocalDate asOfDate)
        throws PersistenceException;
    
    void TransportInventory(int inventoryId, int toWarehouseId, LocalDate asOfDate)
        throws PersistenceException;
    
    void ExportInventory(int inventoryId, LocalDate asOfDate)
        throws PersistenceException;
}
