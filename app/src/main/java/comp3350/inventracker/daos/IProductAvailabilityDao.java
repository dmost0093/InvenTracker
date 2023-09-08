package comp3350.inventracker.daos;

import java.time.LocalDate;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.utils.Query;

public interface IProductAvailabilityDao {
    /**
     * Get the available quantities of ALL product models. (including those with 0 products available)
     */
    Query<ProductModelQuantityDto> getAllProductModelQuantities()
        throws PersistenceException;
    
    /**
     * Get the available quantities of product models with at least 1 product available.
     */
    Query<ProductModelQuantityDto> getAvailableProductModelQuantities()
        throws PersistenceException;
    
    /**
     * Get all available Inventory Items with the specified product model id.
     */
    Query<CompleteItemDto> getAvailableItemsByProductModel(int productModelId, LocalDate asOfDate)
        throws PersistenceException;
}

