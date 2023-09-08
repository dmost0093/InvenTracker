package comp3350.inventracker.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.math.BigDecimal;

import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.WarehouseRowModel;

public class FilterTest {
    public static final BigDecimal MSRP = BigDecimal.valueOf(110);
    
    private final CompleteItemDto item = new CompleteItemDto(
        new InventoryRowModel(1, 1),
        new ProductModelRowModel(1, "product1", 1, BigDecimal.valueOf(100), MSRP),
        new CategoryRowModel(1, "category1", 10),
        null,
        new WarehouseRowModel(1, "warehouse1", 100)
    );
    
    @Test
    public void testFilter_NoFilter() {
        Filter filter = new Filter(0, 0, 0, Integer.MAX_VALUE, true);
        assertTrue(filter.test(item));
    }
    
    @Test
    public void testFilter_MatchPrice() {
        Filter filter = new Filter(0, 0, 0, 110, true);
        assertTrue(filter.test(item));
    }
    
    @Test
    public void testFilter_PriceTooHigh() {
        Filter filter = new Filter(0, 0, 200, 300, true);
        assertFalse(filter.test(item));
    }
    
    @Test
    public void testFilter_PriceTooLow() {
        Filter filter = new Filter(0, 0, 0, 100, true);
        assertFalse(filter.test(item));
    }
    
    @Test
    public void testFilter_MatchCategory() {
        Filter filter = new Filter(1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertTrue(filter.test(item));
    }
    
    @Test
    public void testFilter_NotMatchCategory() {
        Filter filter = new Filter(2, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertFalse(filter.test(item));
    }
    
    @Test
    public void testFilter_MatchWarehouse() {
        Filter filter = new Filter(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertTrue(filter.test(item));
    }
    
    @Test
    public void testFilter_NotMatchWarehouse() {
        Filter filter = new Filter(2, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertFalse(filter.test(item));
    }
}