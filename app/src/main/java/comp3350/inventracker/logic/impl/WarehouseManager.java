package comp3350.inventracker.logic.impl;

import java.util.List;

import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.IWarehouseManager;

public class WarehouseManager
    implements IWarehouseManager {
    final IWarehouseDao warehouses;
    
    public WarehouseManager(IWarehouseDao warehouses) {
        this.warehouses = warehouses;
    }
    
    @Override
    public void AddWarehouse(String address, int capacity) {
        address = address.toUpperCase();
        warehouses.Insert(address, capacity);
    }
    
    @Override
    public WarehouseRowModel SelectWarehouse(final int warehouseId) {
        return warehouses.SelectPK(warehouseId);
    }
    
    @Override
    public List<WarehouseRowModel> SelectAllWarehouse() {
        return warehouses.SelectAll()
                         .ToList();
    }
    
    @Override
    public void UpdateWarehouse(int warehouseId, int newCapacity) {
        warehouses.Update(warehouseId, newCapacity);
    }
}
