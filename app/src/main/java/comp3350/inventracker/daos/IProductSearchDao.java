package comp3350.inventracker.daos;

import java.time.LocalDate;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.utils.Query;

public interface IProductSearchDao {
    Query<CompleteItemDto> GetAllAvailableItems(LocalDate asOfDate)
        throws PersistenceException;
    
    Query<CompleteItemDto> GetAvailableItems(int productModelId, LocalDate asOfDate)
        throws PersistenceException;
    
    CompleteItemDto GetAvailableItem(int inventoryId)
        throws PersistenceException;
}
