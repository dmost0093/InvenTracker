package comp3350.inventracker.logic.impl;

import java.util.List;

import comp3350.inventracker.daos.IWarehouseAnalyticsDao;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.exceptions.WarehouseNotFoundException;
import comp3350.inventracker.logic.IWarehouseAnalyticsManager;

public class WarehouseAnalyticsManager
    implements IWarehouseAnalyticsManager {
    
    final IWarehouseAnalyticsDao warehouseAnalyticsDao;
    
    public WarehouseAnalyticsManager(IWarehouseAnalyticsDao warehouseAnalyticsDao) {
        this.warehouseAnalyticsDao = warehouseAnalyticsDao;
    }
    
    @Override
    public List<WarehouseDto> GetAllSpaceInUse() {
        return warehouseAnalyticsDao.GetAllSpaceInUse()
                                    .ToList();
    }
    
    @Override
    public WarehouseDto GetSpaceInUseAtWarehouse(int warehouseId) throws Exception {
        WarehouseDto warehouseDto = warehouseAnalyticsDao.GetSpaceInUseAtWarehouse(warehouseId);
        
        if (warehouseDto == null) {
            throw new WarehouseNotFoundException(warehouseId);
        }
        
        return warehouseDto;
    }
}
