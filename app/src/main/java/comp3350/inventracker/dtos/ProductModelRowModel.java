package comp3350.inventracker.dtos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductModelRowModel
    implements Comparable<ProductModelRowModel>,
               Serializable {
    
    public final int        ProductModelId;
    public final String     Name;
    public final int        CategoryId;
    public final BigDecimal Cost;
    public final BigDecimal MSRP;
    
    public ProductModelRowModel(int productModelId) {
        this.ProductModelId = productModelId;
        this.Name           = "";
        this.CategoryId     = -1;
        this.Cost           = BigDecimal.valueOf(0);
        this.MSRP           = BigDecimal.valueOf(0);
    }
    
    public ProductModelRowModel(int productModelId, String name, int categoryId, double cost, double msrp) {
        this.ProductModelId = productModelId;
        this.Name           = name;
        this.CategoryId     = categoryId;
        this.Cost           = BigDecimal.valueOf(cost);
        this.MSRP           = BigDecimal.valueOf(msrp);
    }
    
    public ProductModelRowModel(int productModelId, String name, int categoryId, BigDecimal cost, BigDecimal msrp) {
        this.ProductModelId = productModelId;
        this.Name           = name;
        this.CategoryId     = categoryId;
        this.Cost           = cost;
        this.MSRP           = msrp;
    }
    
    /**
     * Compares Categories by CategoryId
     */
    @Override
    public int compareTo(@NonNull ProductModelRowModel other) {
        return ProductModelId - other.ProductModelId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductModelRowModel that = (ProductModelRowModel) o;
        return ProductModelId == that.ProductModelId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ProductModelId);
    }
}

