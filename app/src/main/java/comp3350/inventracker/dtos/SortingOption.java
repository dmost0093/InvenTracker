package comp3350.inventracker.dtos;

public enum SortingOption {
    None("None", 0),
    Category("Category", 1),
    ProductId("ProductId", 1),
    Quantity("Quantity", 2),
    Price("Price", 3);
    
    public final String name;
    public final int    value;
    
    SortingOption(String name, int value) {
        this.name  = name;
        this.value = value;
    }
}
