package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.utils.Query;

public interface IInventoryDao {
    Query<InventoryRowModel> SelectAll()
        throws PersistenceException;
}

