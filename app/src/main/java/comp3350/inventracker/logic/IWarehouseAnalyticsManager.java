package comp3350.inventracker.logic;

import java.util.List;

import comp3350.inventracker.dtos.WarehouseDto;

public interface IWarehouseAnalyticsManager {
    List<WarehouseDto> GetAllSpaceInUse();
    WarehouseDto GetSpaceInUseAtWarehouse(int warehouseId) throws Exception;
}
