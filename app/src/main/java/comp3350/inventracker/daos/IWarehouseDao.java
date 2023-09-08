package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.utils.Query;

public interface IWarehouseDao {
    int Insert(String address, int capacity)
        throws PersistenceException;
    int Update(int warehouseId, int capacity)
        throws PersistenceException;
    Query<WarehouseRowModel> SelectAll()
        throws PersistenceException;
    WarehouseRowModel SelectPK(int warehouseId)
        throws PersistenceException;
}
