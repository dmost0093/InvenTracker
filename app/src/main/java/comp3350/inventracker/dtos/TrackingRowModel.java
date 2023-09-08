package comp3350.inventracker.dtos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TrackingRowModel
    implements Comparable<TrackingRowModel>,
               Serializable {
    
    /** CK - The Inventory's Unique Identifier */
    public final int InventoryId;
    
    /** CK - The order in sequence of this item's movement between locations */
    public final int Seq;
    
    /** FK - Warehouse Identifier */
    public final int WarehouseId;
    
    public LocalDate ArrivalDate          = null;
    public LocalDate DepartDate           = null;
    
    public TrackingRowModel(
        int inventoryId,
        int seq,
        int warehouseId,
        LocalDate arrivalDate,
        LocalDate departDate
    ) {
        this.InventoryId          = inventoryId;
        this.Seq                  = seq;
        this.WarehouseId          = warehouseId;
        this.ArrivalDate          = arrivalDate;
        this.DepartDate           = departDate;
    }
    
    public TrackingRowModel(int inventoryId, int seq, int warehouseId) {
        InventoryId = inventoryId;
        Seq         = seq;
        WarehouseId = warehouseId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrackingRowModel that = (TrackingRowModel) o;
        return InventoryId == that.InventoryId && Seq == that.Seq;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(InventoryId, Seq);
    }
    
    @Override
    public int compareTo(@NonNull TrackingRowModel another) {
        int compare = InventoryId - another.InventoryId;
        
        if (compare == 0) {
            compare = Seq - another.Seq;
        }
        
        return compare;
    }
    
    @Override
    public String toString() {
        return "TrackingRowModel{" +
               "InventoryId=" + InventoryId +
               ", Seq=" + Seq +
               ", WarehouseId=" + WarehouseId +
               ", ArrivalDate=" + ArrivalDate +
               ", DepartDate=" + DepartDate +
               '}';
    }
}
