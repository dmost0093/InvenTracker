package comp3350.inventracker.logic;

import java.util.List;
import comp3350.inventracker.dtos.WarehouseRowModel;

public interface IWarehouseManager {
    void AddWarehouse(String address, int capacity);
    WarehouseRowModel SelectWarehouse(final int warehouseId);
    List<WarehouseRowModel> SelectAllWarehouse();
    void UpdateWarehouse(int warehouseId, int newCapacity);
}
