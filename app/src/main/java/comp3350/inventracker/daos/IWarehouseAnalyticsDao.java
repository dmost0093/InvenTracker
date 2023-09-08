package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.utils.Query;

public interface IWarehouseAnalyticsDao {
    Query<WarehouseDto> GetAllSpaceInUse() throws PersistenceException;
    
    WarehouseDto GetSpaceInUseAtWarehouse(int warehouseId);
}
