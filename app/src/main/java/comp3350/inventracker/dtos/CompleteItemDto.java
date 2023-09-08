package comp3350.inventracker.dtos;

import java.io.Serializable;

public class CompleteItemDto
    implements Serializable {
    public InventoryRowModel    InventoryRowModel;
    public ProductModelRowModel ProductModelRowModel;
    public CategoryRowModel     CategoryRowModel;
    public TrackingRowModel     TrackingRowModel;
    public WarehouseRowModel    WarehouseRowModel;
    
    public CompleteItemDto() {
    }
    
    public CompleteItemDto(
        InventoryRowModel inventoryRowModel,
        ProductModelRowModel productModelRowModel,
        CategoryRowModel categoryRowModel,
        TrackingRowModel trackingRowModel,
        WarehouseRowModel warehouseRowModel
    ) {
        InventoryRowModel    = inventoryRowModel;
        ProductModelRowModel = productModelRowModel;
        CategoryRowModel     = categoryRowModel;
        TrackingRowModel     = trackingRowModel;
        WarehouseRowModel    = warehouseRowModel;
    }
    
    public CompleteItemDto Set(InventoryRowModel inventoryRowModel) {
        InventoryRowModel = inventoryRowModel;
        return this;
    }
    
    public CompleteItemDto Set(ProductModelRowModel productModelRowModel) {
        ProductModelRowModel = productModelRowModel;
        return this;
    }
    
    public CompleteItemDto Set(CategoryRowModel categoryRowModel) {
        CategoryRowModel = categoryRowModel;
        return this;
    }
    
    public CompleteItemDto Set(TrackingRowModel trackingRowModel) {
        TrackingRowModel = trackingRowModel;
        return this;
    }
    
    public CompleteItemDto Set(WarehouseRowModel warehouseRowModel) {
        WarehouseRowModel = warehouseRowModel;
        return this;
    }
}
