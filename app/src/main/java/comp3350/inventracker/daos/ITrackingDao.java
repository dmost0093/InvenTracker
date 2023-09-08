package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.TrackingRowModel;
import comp3350.inventracker.utils.Query;

public interface ITrackingDao {
    Query<TrackingRowModel> SelectAll()
        throws PersistenceException;
    
    /**
     * Get the ordered path (ordered by sequence) of the item with inventoryId
     *
     * @param inventoryId the inventory item to search for.
     */
    Query<TrackingRowModel> GetInventoryPath(int inventoryId)
        throws PersistenceException;
}
