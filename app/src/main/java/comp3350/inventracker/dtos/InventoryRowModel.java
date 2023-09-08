package comp3350.inventracker.dtos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;
/**
 * Representing a piece of Inventory that is currently 'in-stock' at some warehouse.
 * <p>
 * PK: InventoryId
 * </p>
 */
public class InventoryRowModel
    implements Comparable<InventoryRowModel>,
               Serializable  {
    
    /**PK - The Unique Identifier*/
    public final int InventoryId; // instance
    
    /**FK - Product Model Identifier*/
    public final int ProductModelId; // class
    
    public InventoryRowModel(int inventoryId, int productModelId) {
        this.InventoryId    = inventoryId;
        this.ProductModelId = productModelId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventoryRowModel that = (InventoryRowModel) o;
        return InventoryId == that.InventoryId && ProductModelId == that.ProductModelId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(InventoryId, ProductModelId);
    }
    
    @Override
    public int compareTo(@NonNull InventoryRowModel another) {
        return InventoryId - another.InventoryId;
    }
    
    @Override
    public String toString() {
        return "InventoryRowModel{" +
               "InventoryId=" + InventoryId +
               ", ProductModelId=" + ProductModelId +
               '}';
    }
}
