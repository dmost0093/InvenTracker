package comp3350.inventracker.logic.impl;

import java.time.LocalDate;
import java.util.List;

import comp3350.inventracker.daos.IProductSearchDao;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.InventoryItemDto;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.dtos.ProductSorter;
import comp3350.inventracker.dtos.ProductSorterFactory;
import comp3350.inventracker.dtos.SortingOption;
import comp3350.inventracker.logic.IProductSearchResultsManager;
import comp3350.inventracker.utils.Query;

public class ProductSearchResultsManager
    implements IProductSearchResultsManager {
    
    final private IProductSearchDao productSearchDao;
    
    public ProductSearchResultsManager(IProductSearchDao productSearchDao) {
        this.productSearchDao = productSearchDao;
    }
    
    @Override
    public List<ProductModelQuantityDto> GetAvailableQuantities(LocalDate asOfDate, Filter filter, SortingOption sortBy) {
        Query<CompleteItemDto> items = productSearchDao.GetAllAvailableItems(asOfDate);
    
        if (filter != null) {
            items = items.Where(filter);
        }
    
        Query<ProductModelQuantityDto> query = items.GroupBy(
            x -> x.ProductModelRowModel.ProductModelId,
            x -> x,
            (key, agg) -> {
                CompleteItemDto first = agg.First();
                return new ProductModelQuantityDto(key, agg.CountIf(x -> x.WarehouseRowModel.Exists()))
                    .SetProductModel(first.ProductModelRowModel)
                    .SetCategory(first.CategoryRowModel);
            });
        
        if (sortBy != null && sortBy != SortingOption.None) {
            ProductSorter sorter = ProductSorterFactory.GetComparator(sortBy);
            
            if (sorter != null) {
                query = query.OrderBy(sorter);
            }
        }
        
        return query.ToList();
    }
    
    @Override
    public List<InventoryItemDto> GetAvailableItems(int productModelId, LocalDate asOfDate, Filter filter) {
        Query<CompleteItemDto> query = productSearchDao.GetAvailableItems(productModelId, asOfDate);
    
        if (filter != null) {
            query = query.Where(filter);
        }
        
        return query.Select(InventoryItemDto::new).ToList();
    }
    
    @Override
    public CompleteItemDto GetAvailableItem(int inventoryId) {
        return productSearchDao.GetAvailableItem(inventoryId);
    }
}
