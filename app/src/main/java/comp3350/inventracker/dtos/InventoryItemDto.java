package comp3350.inventracker.dtos;

import java.io.Serializable;

public class InventoryItemDto
    implements Serializable {
    
    public final InventoryRowModel InventoryRow;
    public final TrackingRowModel  LatestTracking;
    public final WarehouseRowModel WarehouseRow;
    
    public InventoryItemDto(CompleteItemDto dto) {
        InventoryRow = dto.InventoryRowModel;
        LatestTracking = dto.TrackingRowModel;
        WarehouseRow = dto.WarehouseRowModel;
    }
    
    @Override
    public String toString() {
        return "InventoryItemDto{" +
               "InventoryRow=" + InventoryRow +
               ", LatestTracking=" + LatestTracking +
               ", WarehouseRow=" + WarehouseRow +
               '}';
    }
}
