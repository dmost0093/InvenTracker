package comp3350.inventracker.dtos;

import java.io.Serializable;

public class ProductModelQuantityDto
    implements Serializable {
    public final int                  ProductModelId;
    public final int                  Quantity;
    public       ProductModelRowModel ProductModel;
    public CategoryRowModel     Category;
    
    public ProductModelQuantityDto(int productModelId, int quantity) {
        ProductModelId = productModelId;
        Quantity       = quantity;
    }
    
    public ProductModelQuantityDto SetProductModel(ProductModelRowModel productModel) {
        ProductModel = productModel;
        return this;
    }

    public ProductModelQuantityDto SetCategory(CategoryRowModel category) {
        Category = category;
        return this;
    }
}
