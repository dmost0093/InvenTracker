package comp3350.inventracker.dtos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class WarehouseRowModel
    implements Comparable<WarehouseRowModel>,
               Serializable {
    
    public final int    WarehouseID;
    public final String Address;
    public final int    Capacity;
    
    public WarehouseRowModel(int WarehouseID){
        this.WarehouseID  = WarehouseID;
        this.Address      = "";
        this.Capacity     = 0;
    }
    
    public WarehouseRowModel(int WarehouseID, String Address, int Capacity){
        this.WarehouseID  = WarehouseID;
        this.Address      = Address;
        this.Capacity     = Capacity;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        WarehouseRowModel that = (WarehouseRowModel) o;
        return WarehouseID == that.WarehouseID;
    }
    
    @Override
    public int hashCode() { return Objects.hash(WarehouseID); }
    
    @Override
    public int compareTo(@NonNull WarehouseRowModel compare) {
        return WarehouseID - compare.WarehouseID;
    }
    
    @Override
    public String toString() {
        return "WarehouseRowModel{" +
               "WarehouseID=" + WarehouseID +
               ", Address='" + Address + '\'' +
               ", Capacity=" + Capacity +
               '}';
    }
    
    public boolean Exists() {
        return this.WarehouseID > 0;
    }
}
