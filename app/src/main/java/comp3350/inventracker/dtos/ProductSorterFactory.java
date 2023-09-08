package comp3350.inventracker.dtos;

public class ProductSorterFactory {
    public static ProductSorter GetComparator(SortingOption sortingOption) {
        switch (sortingOption) {
            case Category:
                return new CategoryComparer();
            case ProductId:
                return new ProductIdComparer();
            case Quantity:
                return new QuantityComparer();
            case Price:
                return new PriceComparer();
            default:
                return null;
        }
    }
}


