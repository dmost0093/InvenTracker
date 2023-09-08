package comp3350.inventracker.logic;

import java.time.LocalDate;
import java.util.List;

import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.InventoryItemDto;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.dtos.SortingOption;

public interface IProductSearchResultsManager {
    List<ProductModelQuantityDto> GetAvailableQuantities(LocalDate asOfDate, Filter filter, SortingOption sortBy);
    List<InventoryItemDto> GetAvailableItems(int productModelId, LocalDate asOfDate, Filter filter);
    CompleteItemDto GetAvailableItem(int inventoryId);
}
