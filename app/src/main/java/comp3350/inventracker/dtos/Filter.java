package comp3350.inventracker.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Predicate;

public class Filter implements Serializable, Predicate<CompleteItemDto> {
    final int        CategoryId;
    final int        WarehouseId;
    final BigDecimal MinPrice;
    final BigDecimal MaxPrice;
    final boolean    IncludeOutOfStock;
    
    public Filter(int categoryId, int warehouseId, int minPrice, int maxPrice, boolean includeOutOfStock) {
        CategoryId        = categoryId;
        WarehouseId       = warehouseId;
        MinPrice          = BigDecimal.valueOf(minPrice);
        MaxPrice          = BigDecimal.valueOf(maxPrice);
        IncludeOutOfStock = includeOutOfStock;
    }
    
    @Override
    public String toString() {
        return "Filter{" +
               "CategoryId=" + CategoryId +
               ", WarehouseId=" + WarehouseId +
               ", MinPrice=" + MinPrice +
               ", MaxPrice=" + MaxPrice +
               '}';
    }
    
    @Override
    public boolean test(CompleteItemDto item) {
        BigDecimal msrp = item.ProductModelRowModel.MSRP;
        
        boolean priceMatch = MinPrice.compareTo(msrp) <= 0 && MaxPrice.compareTo(msrp) >= 0;
        
        boolean categoryMatch = this.CategoryId == 0 || item.CategoryRowModel.CategoryId == this.CategoryId;
        
        boolean warehouseMatch = item.WarehouseRowModel.Exists()
            ? this.WarehouseId == 0 || item.WarehouseRowModel.WarehouseID == this.WarehouseId
            : IncludeOutOfStock;
        
        return categoryMatch && warehouseMatch && priceMatch;
    }
}
