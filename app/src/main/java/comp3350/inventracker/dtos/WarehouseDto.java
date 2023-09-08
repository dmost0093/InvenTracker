package comp3350.inventracker.dtos;

import java.io.Serializable;

public class WarehouseDto
    implements Serializable {
    
    public final WarehouseRowModel Warehouse;
    public final int               SpaceInUse;
    
    public WarehouseDto(WarehouseRowModel warehouseRowModel, int spaceInUse) {
        Warehouse = warehouseRowModel;
        SpaceInUse = spaceInUse;
    }
    
    public double getPercentInUse() {
        return SpaceInUse / (double) Warehouse.Capacity;
    }
}
