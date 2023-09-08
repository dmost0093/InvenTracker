package comp3350.inventracker.dtos;

import java.io.Serializable;
import java.util.Comparator;

public abstract class ProductSorter
    implements Comparator<ProductModelQuantityDto>, Serializable {
    public abstract int compare(ProductModelQuantityDto o1, ProductModelQuantityDto o2);
}

class CategoryComparer extends ProductSorter {
    @Override
    public int compare(ProductModelQuantityDto o1, ProductModelQuantityDto o2) {
        return o1.Category.compareTo(o2.Category);
    }
}

class ProductIdComparer extends ProductSorter {
    @Override
    public int compare(ProductModelQuantityDto o1, ProductModelQuantityDto o2) {
        return o1.ProductModelId - o2.ProductModelId;
    }
}

class QuantityComparer extends ProductSorter {
    @Override
    public int compare(ProductModelQuantityDto o1, ProductModelQuantityDto o2) {
        return o2.Quantity - o1.Quantity;
    }
}

class PriceComparer extends ProductSorter {
    @Override
    public int compare(ProductModelQuantityDto o1, ProductModelQuantityDto o2) {
        return o1.ProductModel.MSRP.compareTo(o2.ProductModel.MSRP);
    }
}
